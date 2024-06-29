package com.tulin.v8.webtools.ide.css;


public class CSSDefinition {
	
	/**
	 * @see http://it028.com/css-font.html
	 * @see @see https://www.w3cschool.cn/css/
	 */
	public static final CSSInfo[] CSS_KEYWORDS = {
			/* 文本修饰 */
			new CSSInfo("color", "文本颜色"),
			new CSSInfo("direction", "文本方向"),
			new CSSInfo("letter-spacing", "字符间距"),
			new CSSInfo("line-height", "行高"),
			new CSSInfo("text-align", "对齐元素中的文本"),
			new CSSInfo("text-decoration", "向文本添加修饰"),
			new CSSInfo("text-indent", "缩进元素中文本的首行"),
			new CSSInfo("text-shadow", "文本阴影"),
			new CSSInfo("text-transform", "控制元素中的字母"),
			new CSSInfo("unicode-bidi", "设置或返回文本是否被重写 "),
			new CSSInfo("vertical-align", "元素的垂直对齐"),
			new CSSInfo("white-space", "元素中空白的处理方式"),
			new CSSInfo("word-spacing", "字间距"),
			/* 字体系列 */
			new CSSInfo("font", "在一个声明中设置所有的字体属性"),
			new CSSInfo("font-family", "指定文本的字体系列"),
			new CSSInfo("font-size", "指定文本的字体大小"),
			new CSSInfo("font-style", "指定文本的字体样式"),
			new CSSInfo("font-variant", "以小型大写字体或者正常字体显示文本"),
			new CSSInfo("font-weight", "指定字体的粗细"),
			new CSSInfo("font-stretch"),
			new CSSInfo("font-size-adjust"),
			/* 列表 */
			new CSSInfo("list-style", "简写属性,用于把所有用于列表的属性设置于一个声明中"),
			new CSSInfo("list-style-image", "将图像设置为列表项标志"),
			new CSSInfo("list-style-position", "设置列表中列表项标志的位置"),
			new CSSInfo("list-style-type", "设置列表项标志的类型"),
			/* 背景 */
			new CSSInfo("background", "简写属性，作用是将背景属性设置在一个声明中 如：background:#ffffff url('img_tree.png') no-repeat right top;"),
			new CSSInfo("background-attachment", "背景图像是否固定或者随着页面的其余部分滚动"),
			new CSSInfo("background-color", "设置元素的背景颜色"),
			new CSSInfo("background-image", "把图像设置为背景"),
			new CSSInfo("background-position", "设置背景图像的起始位置 background-position:right top;"),
			new CSSInfo("background-repeat", "设置背景图像是否及如何重复"),
			/* 边框 */
			new CSSInfo("border", "简写属性，用于把针对四个边的属性设置在一个声明 如：border:5px solid red;"),
			new CSSInfo("border-style", "用于设置元素所有边框的样式，或者单独地为各边设置边框样式"),
			new CSSInfo("border-width", "简写属性，用于为元素的所有边框设置宽度，或者单独地为各边边框设置宽度"),
			new CSSInfo("border-color", "简写属性，设置元素的所有边框中可见部分的颜色，或为 4 个边分别设置颜色"),
			new CSSInfo("border-bottom", "简写属性，用于把下边框的所有属性设置到一个声明中"),
			new CSSInfo("border-bottom-color", "设置元素的下边框的颜色"),
			new CSSInfo("border-bottom-style", "设置元素的下边框的样式"),
			new CSSInfo("border-bottom-width", "设置元素的下边框的宽度"),
			new CSSInfo("border-left", "简写属性，用于把左边框的所有属性设置到一个声明中"),
			new CSSInfo("border-left-color", "设置元素的左边框的颜色"),
			new CSSInfo("border-left-style", "设置元素的左边框的样式"),
			new CSSInfo("border-left-width", "设置元素的左边框的宽度"),
			new CSSInfo("border-right", "简写属性，用于把右边框的所有属性设置到一个声明中"),
			new CSSInfo("border-right-color", "设置元素的右边框的颜色"),
			new CSSInfo("border-right-style", "设置元素的右边框的样式"),
			new CSSInfo("border-right-width", "设置元素的右边框的宽度"),
			new CSSInfo("border-top", "简写属性，用于把上边框的所有属性设置到一个声明中"),
			new CSSInfo("border-top-color", "设置元素的上边框的颜色"),
			new CSSInfo("border-top-style", "设置元素的上边框的样式"),
			new CSSInfo("border-top-width", "设置元素的上边框的宽度"),
			new CSSInfo("border-radius", "设置圆角的边框"),
			new CSSInfo("border-collapse"),
			new CSSInfo("border-spacing"),
			/* 轮廓 */
			new CSSInfo("outline", "在一个声明中设置所有的轮廓属性\r\noutline-color\r\noutline-style\r\noutline-width\r\ninherit"),
			new CSSInfo("outline-color", "轮廓的颜色"),
			new CSSInfo("outline-style", "轮廓的样式"),
			new CSSInfo("outline-width", "轮廓的宽度"),
			/* 外边距 */
			new CSSInfo("margin", "简写属性。在一个声明中设置所有外边距属性: 上边 右边 下边 左边"),
			new CSSInfo("margin-bottom", "元素的下外边距"),
			new CSSInfo("margin-left", "元素的左外边距"),
			new CSSInfo("margin-right", "元素的右外边距"),
			new CSSInfo("margin-top", "元素的上外边距"),
			/* 填充 */
			new CSSInfo("padding", "使用简写属性设置在一个声明中的所有填充属性: 上填充 右填充 下填充 左填充"),
			new CSSInfo("padding-bottom", "元素的底部填充"),
			new CSSInfo("padding-left", "元素的左部填充"),
			new CSSInfo("padding-right", "元素的右部填充"),
			new CSSInfo("padding-top", "元素的顶部填充"),
			/* 尺寸 */
			new CSSInfo("height", "元素的高度"),
			new CSSInfo("max-height", "元素的最大高度"),
			new CSSInfo("max-width", "元素的最大宽度"),
			new CSSInfo("min-height", "元素的最小高度"),
			new CSSInfo("min-width", "元素的最小宽度"),
			new CSSInfo("width", "元素的宽度"),
			/* 显示 */
			new CSSInfo("display", "显示 -> 隐藏元素 - display:none或visibility:hidden"),
			new CSSInfo("visibility"),
			/* 定位 */
			new CSSInfo("bottom", "定义了定位元素下外边距边界与其包含块下边界之间的偏移\r\nauto\r\nlength\r\n%\r\ninherit"),
			new CSSInfo("clip", "剪辑一个绝对定位的元素\r\nshape\r\nauto\r\ninherit"),
			new CSSInfo("cursor", "显示光标移动到指定的类型"),
			new CSSInfo("left", "定义了定位元素左外边距边界与其包含块左边界之间的偏移"),
			new CSSInfo("overflow", "当元素的内容溢出其区域时发生的事情\r\nauto\r\nhidden\r\nscroll\r\nvisible\r\ninherit"),
			new CSSInfo("overflow-y", "指定如何处理顶部/底部边缘的内容溢出元素的内容区域"),
			new CSSInfo("overflow-x", "指定如何处理右边/左边边缘的内容溢出元素的内容区域"),
			new CSSInfo("position", "指定元素的定位类型\r\nstatic\r\nrelative\r\nfixed\r\nabsolute\r\nsticky"),
			new CSSInfo("right", "定义了定位元素右外边距边界与其包含块右边界之间的偏移"),
			new CSSInfo("top", "定义了一个定位元素的上外边距边界与其包含块上边界之间的偏移"),
			new CSSInfo("z-index", "设置元素的堆叠顺序\r\nnumber\r\nauto\r\ninherit"),
			/* 浮动 */
			new CSSInfo("clear", "指定不允许元素周围有浮动元素\r\nleft\r\nright\r\nboth\r\nnone\r\ninherit"),
			new CSSInfo("float", "指定一个盒子（元素）是否可以浮动\r\nleft\r\nright\r\nnone\r\ninherit"),
			/* 透明 */
			new CSSInfo("opacity", "CSS3中属性的透明度是 opacity->opacity:0.4; \r\nfilter:alpha(opacity=40); /* IE8 及其更早版本 */"),
			new CSSInfo("filter", "透明度 IE8 及其更早版本 filter:alpha(opacity=40);"),
			new CSSInfo("caption-side"),
			new CSSInfo("table-layout"),
			new CSSInfo("empty-cells"),
			new CSSInfo("content"),
			new CSSInfo("quotes"),
			new CSSInfo("marker-offset"),
			new CSSInfo("background-clip"),
			new CSSInfo("background-origin"),
			new CSSInfo("background-size"),
			new CSSInfo("box-shadow"),
			new CSSInfo("transform"),
			new CSSInfo("animation"),
			new CSSInfo("animation-name"),
			new CSSInfo("animation-duration"),
			new CSSInfo("animation-timing-function"),
			new CSSInfo("animation-delay"),
			new CSSInfo("animation-iteration-count"),
			new CSSInfo("animation-direction"),
			new CSSInfo("animation-fill-mode"),
			new CSSInfo("animation-play-state"),
			new CSSInfo("attr()"),
			new CSSInfo("calc()"),
			new CSSInfo("cubic-bezier()"),
			new CSSInfo("conic-gradient()"),
			new CSSInfo("counter()"),
			new CSSInfo("hsl()"),
			new CSSInfo("hsla()"),
			new CSSInfo("linear-gradient()"),
			new CSSInfo("max()"),
			new CSSInfo("min()"),
			new CSSInfo("radial-gradient()"),
			new CSSInfo("repeating-linear-gradient()"),
			new CSSInfo("repeating-radial-gradient()"),
			new CSSInfo("repeating-conic-gradient()"),
			new CSSInfo("rgb()"),
			new CSSInfo("rgba()"),
			new CSSInfo("var()"),
			new CSSInfo("repeat()"),
			new CSSInfo("minmax()"),
	};
	
	/**
	 * @see http://it028.com/css-font.html
	 * @see https://www.w3cschool.cn/css/
	 */
	public static final CSSValue[] CSS_VALUES = {
			/* 文本修饰 */
			new CSSValue("color:", new CSSInfo[] {new CSSInfo("red",CSSStyles.getString("color.red")),new CSSInfo("blue",CSSStyles.getString("color.blue")),new CSSInfo("green",CSSStyles.getString("color.green")),new CSSInfo("#",CSSStyles.getString("color.16")),new CSSInfo("rgb(255,0,0)",CSSStyles.getString("color.rgb")),new CSSInfo("inherit",CSSStyles.getString("color.inherit"))}),
			new CSSValue("direction:", new CSSInfo[] {new CSSInfo("ltr",""),new CSSInfo("rtl",""),new CSSInfo("inherit","")}),
			new CSSValue("text-indent:", new CSSInfo[] {new CSSInfo("length",CSSStyles.getString("text-indent.length")),new CSSInfo("%",CSSStyles.getString("text-indent.percentage")),new CSSInfo("inherit",CSSStyles.getString("text-indent.inherit"))}),
			new CSSValue("text-align:", new CSSInfo[] {new CSSInfo("left",CSSStyles.getString("text-align.left")),new CSSInfo("right",CSSStyles.getString("text-align.right")),new CSSInfo("center",CSSStyles.getString("text-align.center")),new CSSInfo("justify",CSSStyles.getString("text-align.justify")),new CSSInfo("inherit",CSSStyles.getString("text-align.inherit"))}),
			new CSSValue("text-decoration:", new CSSInfo[] {new CSSInfo("none",CSSStyles.getString("text-decoration.none")),new CSSInfo("underline",CSSStyles.getString("text-decoration.underline")),new CSSInfo("overline",CSSStyles.getString("text-decoration.overline")),new CSSInfo("line-through",CSSStyles.getString("text-decoration.line-through")),new CSSInfo("blink",CSSStyles.getString("text-decoration.blink")),new CSSInfo("inherit",CSSStyles.getString("text-decoration.inherit"))}),
			new CSSValue("text-shadow:", new CSSInfo[] {new CSSInfo("h-shadow",CSSStyles.getString("text-shadow.h-shadow")),new CSSInfo("v-shadow",CSSStyles.getString("text-shadow.v-shadow")),new CSSInfo("blur",CSSStyles.getString("text-shadow.blur")),new CSSInfo("color",CSSStyles.getString("text-shadow.color"))}),
			new CSSValue("letter-spacing:", new CSSInfo[] {new CSSInfo("normal",CSSStyles.getString("letter-spacing.normal")),new CSSInfo("length",CSSStyles.getString("letter-spacing.length")),new CSSInfo("inherit",CSSStyles.getString("letter-spacing.inherit"))}),
			new CSSValue("word-spacing:", new CSSInfo[] {new CSSInfo("normal",CSSStyles.getString("word-spacing.normal")),new CSSInfo("length",CSSStyles.getString("word-spacing.length")),new CSSInfo("inherit",CSSStyles.getString("word-spacing.inherit"))}),
			new CSSValue("text-transform:", new CSSInfo[] {new CSSInfo("none",CSSStyles.getString("text-transform.none")),new CSSInfo("capitalize",CSSStyles.getString("text-transform.capitalize")),new CSSInfo("uppercase",CSSStyles.getString("text-transform.uppercase")),new CSSInfo("lowercase",CSSStyles.getString("text-transform.lowercase")),new CSSInfo("inherit",CSSStyles.getString("text-transform.inherit"))}),
			new CSSValue("white-space:", new CSSInfo[] {new CSSInfo("normal",""),new CSSInfo("pre",""),new CSSInfo("nowrap",""),new CSSInfo("pre-wrap",""),new CSSInfo("pre-line",""),new CSSInfo("inherit","")}),
			new CSSValue("background-color:", new CSSInfo[] {new CSSInfo("red",""),new CSSInfo("blue",""),new CSSInfo("green",""),new CSSInfo("#",""),new CSSInfo("rgb(255,0,0)","")}),
			new CSSValue("background-image:", new CSSInfo[] {new CSSInfo("url('')",CSSStyles.getString("background-image.url")),new CSSInfo("none",CSSStyles.getString("background-image.none")),new CSSInfo("inherit",CSSStyles.getString("background-image.inherit")),new CSSInfo("linear-gradient(direction, color-stop1, color-stop2, ...)",CSSStyles.getString("background-image.linear-gradient"))}),
			new CSSValue("background-repeat:", new CSSInfo[] {new CSSInfo("repeat",CSSStyles.getString("background-repeat.repeat")),new CSSInfo("repeat-x",CSSStyles.getString("background-repeat.repeat-x")),new CSSInfo("repeat-y",CSSStyles.getString("background-repeat.repeat-y")),new CSSInfo("no-repeat",CSSStyles.getString("background-repeat.no-repeat")),new CSSInfo("inherit",CSSStyles.getString("background-repeat.inherit"))}),
			new CSSValue("background-attachment:", new CSSInfo[] {new CSSInfo("scroll",CSSStyles.getString("background-attachment.scroll")),new CSSInfo("fixed",CSSStyles.getString("background-attachment.fixed")),new CSSInfo("local",CSSStyles.getString("background-attachment.local")),new CSSInfo("initial",CSSStyles.getString("background-attachment.initial")),new CSSInfo("inherit",CSSStyles.getString("background-attachment.inherit"))}),
			new CSSValue("background-position:", new CSSInfo[] {new CSSInfo("left top",CSSStyles.getString("background-position.left")), new CSSInfo("x% y%",CSSStyles.getString("background-position.xy")), new CSSInfo("xpos ypos",CSSStyles.getString("background-position.xpyp")), new CSSInfo("inherit",CSSStyles.getString("background-position.inherit"))}),
			new CSSValue("background:", new CSSInfo[] {new CSSInfo("red",""),new CSSInfo("blue",""),new CSSInfo("green",""),new CSSInfo("#",""),new CSSInfo("rgb(255,0,0)",""),new CSSInfo("url('')","")}),
			new CSSValue("padding-left:"),
			new CSSValue("padding-right:"),
			new CSSValue("padding-top:"),
			new CSSValue("padding-bottom:"),
			new CSSValue("padding:"),
			new CSSValue("border-left:"),
			new CSSValue("border-right:"),
			new CSSValue("border-top:"),
			new CSSValue("border-bottom:"),
			new CSSValue("border:"),
			new CSSValue("margin-left:"),
			new CSSValue("margin-right:"),
			new CSSValue("margin-top:"),
			new CSSValue("margin-bottom:"),
			new CSSValue("margin:"),
			new CSSValue("font-style:", new CSSInfo[] {new CSSInfo("normal",""),new CSSInfo("italic",""),new CSSInfo("oblique",""),new CSSInfo("inherit","")}),
			new CSSValue("font-weight:", new CSSInfo[] {new CSSInfo("normal",""),new CSSInfo("bold",""),new CSSInfo("bolder",""),new CSSInfo("lighter",""),new CSSInfo("inherit","")}),
			new CSSValue("font-variant:", new CSSInfo[] {new CSSInfo("normal",""),new CSSInfo("small-caps",""),new CSSInfo("inherit","")}),
			new CSSValue("font-stretch:"),
			new CSSValue("font-size-adjust:"),
			new CSSValue("font-size:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" pt",""),new CSSInfo(" %",""),new CSSInfo("xx-small",""),new CSSInfo("x-small",""),new CSSInfo("small",""),new CSSInfo("medium",""),new CSSInfo("large",""),new CSSInfo("x-large",""),new CSSInfo("xx-large",""),new CSSInfo("smaller",""),new CSSInfo("larger",""),new CSSInfo("inherit","")}),
			new CSSValue("font-family:", new CSSInfo[] {new CSSInfo("family-name",""),new CSSInfo("generic-family","")}),
			new CSSValue("font:", new CSSInfo[] {new CSSInfo("font-style",""),new CSSInfo("font-variant",""),new CSSInfo("font-weight",""),new CSSInfo("font-size/line-height","")}),
			new CSSValue("border-left-width:"),
			new CSSValue("border-right-width:"),
			new CSSValue("border-top-width:"),
			new CSSValue("border-bottom-width:"),
			new CSSValue("border-left-color:"),
			new CSSValue("border-right-color:"),
			new CSSValue("border-top-color:"),
			new CSSValue("border-bottom-color:"),
			new CSSValue("border-left-style:"),
			new CSSValue("border-right-style:"),
			new CSSValue("border-top-style:"),
			new CSSValue("border-bottom-style:"),
			new CSSValue("display:", new CSSInfo[] {new CSSInfo("none",CSSStyles.getString("display.none")),new CSSInfo("block",CSSStyles.getString("display.block")),new CSSInfo("inline",CSSStyles.getString("display.inline")),new CSSInfo("inline-block",CSSStyles.getString("display.inline-block"))}),
			new CSSValue("position:", new CSSInfo[] {new CSSInfo("static",CSSStyles.getString("position.static")),new CSSInfo("fixed",CSSStyles.getString("position.fixed")),new CSSInfo("relative",CSSStyles.getString("position.relative")),new CSSInfo("absolute",CSSStyles.getString("position.absolute")),new CSSInfo("sticky",CSSStyles.getString("position.sticky"))}),
			new CSSValue("top:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("bottom:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("left:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("right:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("float:", new CSSInfo[] {new CSSInfo("left",""),new CSSInfo("right",""),new CSSInfo("none",""),new CSSInfo("inherit","")}),
			new CSSValue("clear:", new CSSInfo[] {new CSSInfo("left",""),new CSSInfo("right",""),new CSSInfo("both",""),new CSSInfo("none",""),new CSSInfo("inherit","")}),
			new CSSValue("z-index:", new CSSInfo[] {new CSSInfo("1","1-9999")}),
			new CSSValue("unicode-bidi:", new CSSInfo[] {new CSSInfo("normal",""),new CSSInfo("embed",""),new CSSInfo("bidi-override",""),new CSSInfo("initial",""),new CSSInfo("inherit","")}),
			new CSSValue("width:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("min-width:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("max-width:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("height:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("min-height:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("max-height:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("line-height:", new CSSInfo[] {new CSSInfo("normal",""),new CSSInfo("number",""),new CSSInfo("length",""),new CSSInfo("%",""),new CSSInfo("inherit","")}),
			new CSSValue("vertical-align:", new CSSInfo[] {new CSSInfo("baseline",""),new CSSInfo("sub",""),new CSSInfo("super",""),new CSSInfo("top",""),new CSSInfo("text-top",""),new CSSInfo("middle",""),new CSSInfo("bottom",""),new CSSInfo("text-bottom",""),new CSSInfo("length",""),new CSSInfo("%",""),new CSSInfo("inherit","")}),
			new CSSValue("overflow:", new CSSInfo[] {new CSSInfo("visible",CSSStyles.getString("overflow.visible")),new CSSInfo("hidden",CSSStyles.getString("overflow.hidden")),new CSSInfo("scroll",CSSStyles.getString("overflow.scroll")),new CSSInfo("auto",CSSStyles.getString("overflow.auto")),new CSSInfo("inherit",CSSStyles.getString("overflow.inherit"))}),
			new CSSValue("clip:"),
			new CSSValue("visibility:"),
			new CSSValue("caption-side:"),
			new CSSValue("table-layout:"),
			new CSSValue("border-collapse:"),
			new CSSValue("border-spacing:"),
			new CSSValue("empty-cells:"),
			new CSSValue("content:"),
			new CSSValue("quotes:"),
			new CSSValue("list-style-type:"),
			new CSSValue("list-style-image:"),
			new CSSValue("list-style-position:"),
			new CSSValue("list-style:"),
			new CSSValue("marker-offset:"),
			new CSSValue("cursor:", new CSSInfo[] {new CSSInfo("auto",CSSStyles.getString("cursor.auto")),new CSSInfo("default",CSSStyles.getString("cursor.default")),new CSSInfo("pointer",CSSStyles.getString("cursor.pointer")),new CSSInfo("help",CSSStyles.getString("cursor.help")),new CSSInfo("progress",CSSStyles.getString("cursor.progress")),new CSSInfo("wait",CSSStyles.getString("cursor.wait")),new CSSInfo("text",CSSStyles.getString("cursor.text")),new CSSInfo("move",CSSStyles.getString("cursor.move")),new CSSInfo("col-resize",CSSStyles.getString("cursor.col-resize")),new CSSInfo("row-resize",CSSStyles.getString("cursor.row-resize")),new CSSInfo("n-resize",CSSStyles.getString("cursor.n-resize")),new CSSInfo("s-resize",CSSStyles.getString("cursor.s-resize")),new CSSInfo("grab",CSSStyles.getString("cursor.grab")),new CSSInfo("grabbing",CSSStyles.getString("cursor.grabbing")),new CSSInfo("none",CSSStyles.getString("cursor.none"))}),
			new CSSValue("outline-width:"),
			new CSSValue("outline-color:"),
			new CSSValue("outline-style:"),
			new CSSValue("outline:"),
			new CSSValue("transform:", new CSSInfo[] {new CSSInfo("translate()"),new CSSInfo("rotate()"),new CSSInfo("scale()"),new CSSInfo("skew()"),new CSSInfo("matrix()")})
	};
	
	/**
	 * 伪元素
	 */
	public static final CSSInfo[] Pseudo_Element = {
			new CSSInfo("link", "选择所有未访问链接->a:link"),
			new CSSInfo("visited", "选择所有访问过的链接->a:visited"),
			new CSSInfo("active", "选择正在活动链接->a:active"),
			new CSSInfo("hover", "把鼠标放在链接上的状态->a:hover"),
			new CSSInfo("focus", "选择元素输入后具有焦点->input:focus"),
			new CSSInfo("first-letter", "选择每个<p> 元素的第一个字母->p:first-letter"),
			new CSSInfo("first-line", "选择每个<p> 元素的第一行->p:first-line"),
			new CSSInfo("first-child", "选择器匹配属于任意元素的第一个子元素的 <p> 元素->p:first-child"),
			new CSSInfo("before", "在每个<p>元素之前插入内容->p:before"),
			new CSSInfo("after", "在每个<p>元素之后插入内容->p:after"),
			new CSSInfo("lang(language)", "为<p>元素的lang属性选择一个开始值->p:lang(it)")
	};
	
	/**
	 * 媒体类型
	 */
	public static final CSSInfo[] Media_Type = {
			new CSSInfo("@media all", "用于所有的媒体设备"),
			new CSSInfo("@media aural", "用于语音和音频合成器"),
			new CSSInfo("@media braille", "用于盲人用点字法触觉回馈设备"),
			new CSSInfo("@media embossed", "用于分页的盲人用点字法打印机"),
			new CSSInfo("@media handheld", "用于小的手持的设备"),
			new CSSInfo("@media print", "用于打印机"),
			new CSSInfo("@media projection", "用于方案展示，比如幻灯片"),
			new CSSInfo("@media screen", "用于电脑显示器"),
			new CSSInfo("@media tty", "用于使用固定密度字母栅格的媒体，比如电传打字机和终端"),
			new CSSInfo("@media tv", "用于电视机类型的设备")
	};
	
//	static {
//		// sort by length
//		Arrays.sort(CSS_KEYWORDS,new Comparator(){
//			public int compare(Object o1, Object o2){
//				CSSInfo info1 = (CSSInfo)o1;
//				CSSInfo info2 = (CSSInfo)o2;
//				if(info1.getReplaceString().length() > info2.getReplaceString().length()){
//					return -1;
//				}
//				if(info1.getReplaceString().length() < info2.getReplaceString().length()){
//					return 1;
//				}
//				return 0;
//			}
//		});
//	}
}
