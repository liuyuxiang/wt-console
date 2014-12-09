使用时需要引入一下文件
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/common/css/theme/blue/component/dialog/default.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/common/script/component/dialog/lhgcore.min.js" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/script/component/dialog/lhgdialog.min.js" ></script>

示例1
		<div id="test1"></div>
		<input type="button" id="btn1" value="fdsfds"/>
		<script>
			J(function(){ 
				J('#btn1').dialog({ id:'test1', html:'<h3>我是jQuery方式调用的窗口</h3>' }); 
			});
	</script>
如有其它应用请查看一下APIS中的具体说明
	窗口lhgdialog.min.js文件的url参数(3.4.0新增)
参数形式为：<script type="text/javascript" src="lhgdialog.min.js?t=self&s=chrome"></script>

1.t：指定弹出窗口的页面(替代原来的第22个参数SetTopWindow)
参数值：self，在当前页面弹出窗口。此参数只用在框架页面中，如果不写此参数则窗口跨框架弹出在框架最顶层页面，如果值为self则不跨框架，而在当前面页弹出。2.s：窗口使用的皮肤的名称(3.5.0修改)
参数值：默认default，不写此参数则值为default。如果你想在同一页面使用不同皮肤的窗口，就要把你要使用的皮肤的名称都写上，中间用","隔开，例如：lhgdialog.min.js?s=default,chrome，此参数用来动态给窗口换肤，参数的值为skins文件夹下各皮肤文件夹的名，具体使用方法请参阅皮肤制作url参数不需要设定的就可以不写，不写时就使用默认值。

初始化参数列表
初始化参数列表：即为J.dialog({ 这里的参数 });

1.lockScroll：弹出遮罩层时是否消除滚动条(3.5.2新增)
参数值：默认为“false”，如果为true则弹出遮罩层时会去掉浏览器右边的滚动条。2.autoPos：当浏览器大小改变时窗口的位置是否自动定位(3.4.2新增 3.5.2修改)
参数值：默认为false，不自动定位窗口位置。如果想让窗口自动定位，属性值分为2种，一种值为一个对象，对象中有2个属性，分别为left和top，left和top的值与窗口参数left和top的值是一样的。例如：autoPos:{left:'right',top:'bottom'}。另一种是值为true，这里窗口默认为自动居中。这和第一种值形式的：autoPos:{left:'center',top:'center'}的效果是相同的，只不过为true是一种简写方式。3.skin：指定窗口的皮肤(3.5.0新增)
参数值：默认为“default”。4.args：传递的参数(3.5.0新增)
参数值：值可为任意类型的数据。5.onCancel：自定义窗口关闭函数(3.5.0修改)
参数值：如果加了此参数则可以调用此函数来关闭窗口。3.5.0将此属性改为此函数参数的作用是在关闭窗口前执行这个函数来完成一定动作。6.cancelBtnTxt：设置取消按钮的文本(3.4.2新增)
参数值：默认为“取消”。此参数和onCancel配合使用即可改变取消按钮为其它作用的按钮。7.autoCloseFn：自动关闭窗口时执行的函数(3.4.2新增)
参数值：当指定了timer属性后，此参数为窗口关闭前执行的函数。8.bgcolor：设置遮罩层的颜色(3.4.1新增)
参数值：默认为白色(#fff)。9.opacity：设置遮罩层的透明度(3.4.1新增)
参数值：默认为0.5(也就是50%的透明度)，值为小于1的一位小数。10.onMinSize：最小化按钮调用的函数(3.4.1新增)
参数值：此属性为一个函数，就是单击最小化按钮调用的函数，主要是为用户提供个接口，这里你可以自己写这个函数。11.maxBtn：是否显示最大化按钮(3.4.0新增)
参数值：默认true(显示，如果titleBar参数为false，此参数无效)，false(不显示)。注：如果fixed参数为true，那么此参数就自动为false。12.minBtn：是否显示最小化按钮(3.4.0新增)
参数值：默认true(显示，如果titleBar参数为false，此参数无效)，false(不显示)。最小化按钮功能暂无13.timer：定时关闭窗口时间，单位为秒(3.4.0新增)
参数值：无，不带单位的数字，单位为秒。14.id：窗口的id号
参数值：默认lhgdlgId，自定义对话框ID属性，要保证在页面中是唯一的，不能和页面中任何元素的id相同。 
注意：如果页面中只有一个弹出窗口此参数可以不写，但页面中如果有1个以上的弹出窗口则一定要加此参数。15.title：窗口的标题文本
参数值：默认lhgdialog弹出窗口，窗口标题的文件字符。16.width：窗口的宽度
参数值：默认400，不带单位的数字。17.height：窗口的高度
参数值：默认300，不带单位的数字。18.titleBar：是否显示标题栏
参数值：默认true(显示)，false(不显示，注意如果不显示一定要选择相应的皮肤，无标题栏的皮肤)。19.iconTitle：是否显示标题栏左边小图标
参数值：默认true(显示，如果titleBar参数为false，此参数无效），false(不显示)。20.xButton：是否显示窗口右上角的X关闭按钮
参数值：默认true(显示，如果titleBar参数为false，此参数无效），false(不显示)。21.btnBar：是否显示按钮栏
参数值：默认true(显示），false(不显示)。22.cancelBtn：是否显示取消按钮
参数值：默认true(显示，要显示的同时必须设btnBar参数为true），false(不显示)。23.page：窗口内容页的地址
参数值：窗口的内容页为一个单独的页面文件，这个文件的路径是内容页面文件相对于调用窗口插件的路径或也可使用绝对路径，如果此参数的值为不同域的外部链接，那一定要使下面的link参数为真。24.link：是否为外部链接
参数值：默认false(不是外部链接），true(是外部链接，这里的外部链接指的是不同域的网址)。25.html：窗口的内容为HTML代码
参数值：可以是HTML代码或DOM对象。26.fixed：是否开启静止定位
参数值：默认false(不开启），true(开启，静止定位指的就是窗口随屏滚动)。27.left：X轴的坐标
参数值：默认center(居中），left(屏幕的左边)，right(屏幕的右边)，如果开启了fixed则原点以浏览器视口为基准。28.top：Y轴的坐标
参数值：默认center(居中），top(屏幕的最上边)，right(屏幕的最下面)，如果开启了fixed则原点以浏览器视口为基准。29.cover：是否开启锁屏
参数值：默认false(不开启），true(开启，中断用户对话框之外的交互，用于显示非常重要的操作/消息)。30.drag：是否允许拖动对话框
参数值：默认true(允许），false(不允许)。31.resize：是否允许拖动改变窗口大小
参数值：默认true(允许），false(不允许)。32.rang：是否限制窗口挪动范围
参数值：默认false(不限制），true(限制，也就是不允许窗口拖出浏览器的可视区域)。33.loadingText：窗口加载时的文本字符
参数值：默认“窗口正在加载中，请稍等...”。34.autoSize：是否窗口自适应大小
参数值：默认false(不适应），true(自动适应窗口内容的大小)。35.SetTopWindow：指定窗口要在弹出时的那个页面的window对象
此参数已被新的url参数t所替代，3.4.0版本删除了此参数。36.parent：子窗口的父窗口对象
参数值：此参数只用在弹出的窗口中再弹出子窗口时指定父窗口对象，注意如果2层弹出窗口都有遮罩层则一定要加此参数。37.dgOnLoad：窗口加载后执行的函数
参数值：注意此参数值一定要为函数。38.onXclick：窗口右上角X关闭按钮拦截函数
参数值：如果加了此参数则窗口右上角X关闭按钮则执行此函数。其中一些参数的用法请参照示例中的使用方法

API函数接口列表
API接口列表：

1.setArgs(args) ：设置要传递的数据(3.5.0新增)
参数1：要传递的数据，可以为任意类型的数据。2.getArgs() ：获取传递的数据(3.5.0新增)
参数1：无，可以为任意类型的数据。3.addBtn(id,txt,fn,pos) ：在窗口的按钮栏增加按钮(3.4.2新增pos参数)
参数1：按钮的id 
参数2：按钮上的文本 
参数3：按钮绑定的函数 
参数4：用来指定新增加按出现在已有按钮的左边(值为'left')还是右边(值为'right')4.SetCancelBtn(txt,fn) ：重新设定取消按钮(3.4.2新增)
参数1：按钮的文本。 
参数2：按钮重新绑定的函数。5.closeTime(second,bFn,aFn) ：定时关闭窗口(3.4.0新增,3.4.2修改，3.5.2修改)
参数1：关闭窗口的时间，单位为秒。 
参数2：关闭窗口前执行的函数。 
参数3：关闭窗口后执行的函数。(3.5.2新增此参数)6.SetPosition(top,left,fix) ：重新指定窗口的位置(3.4.0新增,3.4.2修改) : SetPosition(left,top)
参数1：X轴的坐标(详细见初始化参数里的第17个参数) 
参数2：Y轴的坐标(详细见初始化参数里的第18个参数) 
参数3：是否是静止定位(详细见初始化参数里的第16个参数，这里要注意如果fixed参数为true时这个参数一定要为true，否则就不要加此参数) 
注：原来的第3个参数在3.4.2中已删除，这个参数程序会自动根据你调用窗口时的参数fixed的设置来判断。7.maxSize() ：窗口最大化函数接口(3.4.1新增)
参数：无，你可以通调用此函数来控制窗口的最大化和还原。8.SetMinBtn(fn) ：重新设定最小化按钮函数(3.4.1新增)
参数1：重新给最小化按钮绑定的函数，你可以通调用此函数重新给最小化按钮绑定函数。9.iWin(id) ：获取指定id的窗口内容页的window对象(3.4.1新增)
参数1：指定窗口的id，此函数用来返回指定id的窗口的内容页的window对象，主要用在传值使用中。10.iDoc(id) ：获取指定id的窗口内容页的document对象(3.4.1新增)
参数1：指定窗口的id，此函数用来返回指定id的窗口的内容页的document对象，主要用在传值使用中。11.iDg(id) ：获取指定id的窗口DOM对象(3.4.1新增)
参数1：指定窗口的id，此函数用来返回指定id的窗口的DOM对象，主要用判断此窗口是否存在。12.SetXbtn(fn,noShow) ：重新设置X按钮动作(3.4.0新增)
参数1：重新给X按钮绑定的函数 
参数2：是否显示X按钮13.SetTitle(txt) ：重新指定标题的文本(3.4.0新增)
参数1：重新指定的标题的文本内容。14.ShowDialog() ：显示窗口
无参数，jQ调用方式不需要加此方法。15.cancel() ：关闭窗口
无参数。16.reDialogSize(width,height) ：重新指定窗口的大小
参数1：窗口的宽度，如：600，不带单位的数字 
参数2：窗口的高度，如：500，不带单位的数字17.removeBtn(id) ：移除窗口中按钮栏的按钮
参数1：按钮的id18.SetIndex() ：设置窗口的层叠次序
无参数API属性接口列表：

1.dialogId ：窗口的id(3.5.0新增)
获取窗口设置的id值，此id不是窗口真正的id，此id是你在调用窗口时设置的id的值。2.parent ：父窗口对象实例(3.5.0新增)
此属性就是取的你设置的parent参数属性的值，即：J.dialog({ parent:dg }) 这里的parent的值。3.dg ：窗口的DOM对象
可通过此对象对窗口和窗口内元素进行操作。4.lhgDG ：创建的窗口的实例对象
此属性只用在page参数指定的内容页面中，它取的是创建此窗口的实例对象。5.topWin ：顶层页面的window对象
此参数在3.4.1版本中删除了，要想得到顶层页面的window对象直接写top就行了。6.topDoc ：顶层页面的document对象
此参数在3.4.1版本中删除了，要想得到顶层页面的document对象直接写top.document就行了。7.curWin ：窗口调用页面的window对象
也就是加载lhgdialog.min.js的页面的window对象，如果不是在框架中弹出它和topWin是相等的。8.curDoc ：窗口调用页面的document对象
也就是加载lhgdialog.min.js的页面的document对象，如果不是在框架中弹出它和topDoc是相等的。9.dgWin ：内容页的window对象
如果参数为page，且link参数不为真，那这个就是内容页的window对象。10.dgDoc ：内容的document对象
如果参数为page，且link参数不为真，那这个就是内容页的document对象。