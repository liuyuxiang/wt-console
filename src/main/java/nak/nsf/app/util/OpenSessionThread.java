package nak.nsf.app.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public abstract class OpenSessionThread extends Thread
{

	private FlushMode flushMode;

	protected final Log logger;
	private SessionFactory sessionFactory;

	private boolean singleSession;

	/**
	 * 
	 */
	@SuppressWarnings("deprecation")
	public OpenSessionThread()
	{
		super();
		this.logger = LogFactory.getLog(getClass());
		this.singleSession = true;
		this.flushMode = FlushMode.NEVER;
	}

	/**
	 * 方法说明：closeSession
	 * 
	 * @param session
	 *            session
	 * @param sessionFactory
	 *            sessionFactory
	 */
	protected void closeSession(Session session, SessionFactory sessionFactory)
	{
		SessionFactoryUtils.closeSession(session);
	}

	protected FlushMode getFlushMode()
	{
		return this.flushMode;
	}

	/**
	 * 方法说明：getSession
	 * 
	 * @param sessionFactory
	 *            sessionFactory
	 * @return Session
	 * @throws DataAccessResourceFailureException
	 */
	protected Session getSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException
	{
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		FlushMode flushMode = getFlushMode();
		if (flushMode != null) {
			session.setFlushMode(flushMode);
		}
		return session;
	}

	protected boolean isSingleSession()
	{
		return this.singleSession;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	/**
	 * 方法说明：run
	 * 
	 */
	public void run()
	{
		super.run();

		boolean participate = false;

		if (isSingleSession()) {
			if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
				participate = true;
			} else {
				this.logger.debug("Opening single Hibernate Session in OpenSessionThread");
				Session session = getSession(sessionFactory);
				TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(
						session));
			}

		} else if (SessionFactoryUtils.isDeferredCloseActive(sessionFactory)) {
			participate = true;
		} else {
			SessionFactoryUtils.initDeferredClose(sessionFactory);
		}
		try {
			runInTransaction();
		} finally {
			if (!(participate))
				if (isSingleSession()) {
					SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
							.unbindResource(sessionFactory);
					this.logger.debug("Closing single Hibernate Session in OpenSessionThread");
					closeSession(sessionHolder.getSession(), sessionFactory);
				} else {
					SessionFactoryUtils.processDeferredClose(sessionFactory);
				}
		}
	}

	/**
	 * 方法说明：runInTransaction
	 * 
	 */
	protected abstract void runInTransaction();

	/**
	 * @param flushMode
	 *            the flushMode to set
	 */
	public void setFlushMode(FlushMode flushMode)
	{
		this.flushMode = flushMode;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @param singleSession
	 *            the singleSession to set
	 */
	public void setSingleSession(boolean singleSession)
	{
		this.singleSession = singleSession;
	}
}
