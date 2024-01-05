package com.tulin.v8.webtools.ide.css;


public class CSSDefinition {
	
	/**
	 * @see http://it028.com/css-font.html
	 * @see @see https://www.w3cschool.cn/css/
	 */
	public static final CSSInfo[] CSS_KEYWORDS = {
			new CSSInfo("text-indent"),
			new CSSInfo("text-align"),
			new CSSInfo("text-decoration"),
			new CSSInfo("text-shadow"),
			new CSSInfo("letter-spacing"),
			new CSSInfo("word-spacing"),
			new CSSInfo("text-transform"),
			new CSSInfo("white-space"),
			new CSSInfo("color"),
			new CSSInfo("background-color"),
			new CSSInfo("background-image"),
			new CSSInfo("background-repeat"),
			new CSSInfo("background-attachment"),
			new CSSInfo("background-position"),
			new CSSInfo("background"),
			new CSSInfo("padding-left"),
			new CSSInfo("padding-right"),
			new CSSInfo("padding-top"),
			new CSSInfo("padding-bottom"),
			new CSSInfo("padding"),
			new CSSInfo("border-left"),
			new CSSInfo("border-right"),
			new CSSInfo("border-top"),
			new CSSInfo("border-bottom"),
			new CSSInfo("border"),
			new CSSInfo("margin-left"),
			new CSSInfo("margin-right"),
			new CSSInfo("margin-top"),
			new CSSInfo("margin-bottom"),
			new CSSInfo("margin"),
			new CSSInfo("font-style"),
			new CSSInfo("font-weight"),
			new CSSInfo("font-variant"),
			new CSSInfo("font-stretch"),
			new CSSInfo("font-size-adjust"),
			new CSSInfo("font-size"),
			new CSSInfo("font-family"),
			new CSSInfo("font"),
			new CSSInfo("border-left-width"),
			new CSSInfo("border-right-width"),
			new CSSInfo("border-top-width"),
			new CSSInfo("border-bottom-width"),
			new CSSInfo("border-left-color"),
			new CSSInfo("border-right-color"),
			new CSSInfo("border-top-color"),
			new CSSInfo("border-bottom-color"),
			new CSSInfo("border-left-style"),
			new CSSInfo("border-right-style"),
			new CSSInfo("border-top-style"),
			new CSSInfo("border-bottom-style"),
			new CSSInfo("display"),
			new CSSInfo("position"),
			new CSSInfo("top"),
			new CSSInfo("bottom"),
			new CSSInfo("left"),
			new CSSInfo("right"),
			new CSSInfo("float"),
			new CSSInfo("clear"),
			new CSSInfo("z-index"),
			new CSSInfo("direction"),
			new CSSInfo("unicode-bidi"),
			new CSSInfo("width"),
			new CSSInfo("min-width"),
			new CSSInfo("max-width"),
			new CSSInfo("height"),
			new CSSInfo("min-height"),
			new CSSInfo("max-height"),
			new CSSInfo("line-height"),
			new CSSInfo("vertical-align"),
			new CSSInfo("overflow"),
			new CSSInfo("clip"),
			new CSSInfo("visibility"),
			new CSSInfo("caption-side"),
			new CSSInfo("table-layout"),
			new CSSInfo("border-collapse"),
			new CSSInfo("border-spacing"),
			new CSSInfo("empty-cells"),
			new CSSInfo("content"),
			new CSSInfo("quotes"),
			new CSSInfo("list-style-type"),
			new CSSInfo("list-style-image"),
			new CSSInfo("list-style-position"),
			new CSSInfo("list-style"),
			new CSSInfo("marker-offset"),
			new CSSInfo("cursor"),
			new CSSInfo("outline-width"),
			new CSSInfo("outline-color"),
			new CSSInfo("outline-style"),
			new CSSInfo("outline"),
			new CSSInfo("a:link"),
			new CSSInfo("a:visited"),
			new CSSInfo("a:hover"),
			new CSSInfo("a:active")
	};
	
	/**
	 * @see http://it028.com/css-font.html
	 * @see https://www.w3cschool.cn/css/
	 */
	public static final CSSValue[] CSS_VALUES = {
			new CSSValue("text-indent:", new CSSInfo[] {new CSSInfo("length",Messages.getString("text-indent.length")),new CSSInfo("%",Messages.getString("text-indent.%")),new CSSInfo("inherit",Messages.getString("text-indent.inherit"))}),
			new CSSValue("text-align:", new CSSInfo[] {new CSSInfo("left",Messages.getString("text-align.left")),new CSSInfo("right",Messages.getString("text-align.right")),new CSSInfo("center",Messages.getString("text-align.center")),new CSSInfo("justify",Messages.getString("text-align.justify")),new CSSInfo("inherit",Messages.getString("text-align.inherit"))}),
			new CSSValue("text-decoration:", new CSSInfo[] {new CSSInfo("none","默认。定义标准的文本。"),new CSSInfo("underline","定义文本下的一条线。"),new CSSInfo("overline","定义文本上的一条线。"),new CSSInfo("line-through","定义穿过文本下的一条线。"),new CSSInfo("blink","定义闪烁的文本。"),new CSSInfo("inherit","规定应该从父元素继承 text-decoration 属性的值。")}),
			new CSSValue("text-shadow:", new CSSInfo[] {new CSSInfo("h-shadow","必需。水平阴影的位置。允许负值。"),new CSSInfo("v-shadow","必需。垂直阴影的位置。允许负值。"),new CSSInfo("blur","可选。模糊的距离。"),new CSSInfo("color","可选。阴影的颜色。")}),
			new CSSValue("letter-spacing:", new CSSInfo[] {new CSSInfo("normal","默认。规定字符间没有额外的空间。"),new CSSInfo("length","定义字符间的固定空间（允许使用负值）。"),new CSSInfo("inherit","规定应该从父元素继承 letter-spacing 属性的值。")}),
			new CSSValue("word-spacing:", new CSSInfo[] {new CSSInfo("normal","默认。定义单词间的标准空间。"),new CSSInfo("length","定义单词间的固定空间。"),new CSSInfo("inherit","规定应该从父元素继承 word-spacing 属性的值。")}),
			new CSSValue("text-transform:", new CSSInfo[] {new CSSInfo("none","默认。定义带有小写字母和大写字母的标准的文本。"),new CSSInfo("capitalize","文本中的每个单词以大写字母开头。"),new CSSInfo("uppercase","定义仅有大写字母。"),new CSSInfo("lowercase","定义无大写字母，仅有小写字母。"),new CSSInfo("inherit","规定应该从父元素继承 text-transform 属性的值。")}),
			new CSSValue("white-space:", new CSSInfo[] {new CSSInfo("normal",""),new CSSInfo("pre",""),new CSSInfo("nowrap",""),new CSSInfo("pre-wrap",""),new CSSInfo("pre-line",""),new CSSInfo("inherit","")}),
			new CSSValue("color:", new CSSInfo[] {new CSSInfo("red","规定颜色值为颜色名称的颜色（比如 red：红色）"),new CSSInfo("blue","规定颜色值为颜色名称的颜色（比如 blue：蓝色）"),new CSSInfo("green","规定颜色值为颜色名称的颜色（比如 green：绿色）"),new CSSInfo("#","规定颜色值为十六进制值的颜色（比如 #ff0000）"),new CSSInfo("rgb(255,0,0)","规定颜色值为 rgb 代码的颜色（比如 rgb(255,0,0)）"),new CSSInfo("inherit","规定应该从父元素继承颜色")}),
			new CSSValue("background-color:", new CSSInfo[] {new CSSInfo("red",""),new CSSInfo("blue",""),new CSSInfo("green",""),new CSSInfo("#",""),new CSSInfo("rgb(255,0,0)","")}),
			new CSSValue("background-image:", new CSSInfo[] {new CSSInfo("url('')","图像的URL"),new CSSInfo("none","无图像背景会显示。这是默认"),new CSSInfo("inherit","指定背景图像应该从父元素继承")}),
			new CSSValue("background-repeat:", new CSSInfo[] {new CSSInfo("repeat","背景图像将向垂直和水平方向重复。这是默认"),new CSSInfo("repeat-x","只有水平位置会重复背景图像"),new CSSInfo("repeat-y","只有垂直位置会重复背景图像"),new CSSInfo("no-repeat","background-image不会重复"),new CSSInfo("inherit","指定background-repea属性设置应该从父元素继承")}),
			new CSSValue("background-attachment:", new CSSInfo[] {new CSSInfo("scroll","背景图片随着页面的滚动而滚动，这是默认的。"),new CSSInfo("fixed","背景图片不会随着页面的滚动而滚动。"),new CSSInfo("local","背景图片会随着元素内容的滚动而滚动。"),new CSSInfo("initial","设置该属性的默认值。"),new CSSInfo("inherit","指定 background-attachment 的设置应该从父元素继承。")}),
			new CSSValue("background-position:", new CSSInfo[] {new CSSInfo("left top","如果仅指定一个关键字，其他值将会是\"center\""), new CSSInfo("x% y%","第一个值是水平位置，第二个值是垂直。左上角是0％0％。右下角是100％100％。如果仅指定了一个值，其他值将是50％。 。默认值为：0％0％"), new CSSInfo("xpos ypos","第一个值是水平位置，第二个值是垂直。左上角是0。单位可以是像素（0px0px）或任何其他 CSS单位。如果仅指定了一个值，其他值将是50％。你可以混合使用％和positions"), new CSSInfo("inherit","指定background-position属性设置应该从父元素继承")}),
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
			new CSSValue("display:", new CSSInfo[] {new CSSInfo("none","隐藏"),new CSSInfo("block","显示为块级元素"),new CSSInfo("inline","显示为内联元素"),new CSSInfo("inline-block","显示为内联块元素，表现为同行显示并可修改宽高内外边距等属性")}),
			new CSSValue("position:", new CSSInfo[] {new CSSInfo("static","元素的默认值，即没有定位，遵循正常的文档流对象。"),new CSSInfo("fixed","元素的位置相对于浏览器窗口是固定位置。"),new CSSInfo("relative","相对定位元素的定位是相对其正常位置。"),new CSSInfo("absolute","绝对定位的元素的位置相对于最近的已定位父元素，如果元素没有已定位的父元素，那么它的位置相对于页面"),new CSSInfo("sticky","基于用户的滚动位置来定位。")}),
			new CSSValue("top:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("bottom:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("left:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("right:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("float:", new CSSInfo[] {new CSSInfo("left",""),new CSSInfo("right",""),new CSSInfo("none",""),new CSSInfo("inherit","")}),
			new CSSValue("clear:", new CSSInfo[] {new CSSInfo("left",""),new CSSInfo("right",""),new CSSInfo("both",""),new CSSInfo("none",""),new CSSInfo("inherit","")}),
			new CSSValue("z-index:", new CSSInfo[] {new CSSInfo("1","1-9999")}),
			new CSSValue("direction:", new CSSInfo[] {new CSSInfo("ltr",""),new CSSInfo("rtl",""),new CSSInfo("inherit","")}),
			new CSSValue("unicode-bidi:", new CSSInfo[] {new CSSInfo("normal",""),new CSSInfo("embed",""),new CSSInfo("bidi-override",""),new CSSInfo("initial",""),new CSSInfo("inherit","")}),
			new CSSValue("width:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("min-width:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("max-width:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("height:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("min-height:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("max-height:", new CSSInfo[] {new CSSInfo(" px",""),new CSSInfo(" %","")}),
			new CSSValue("line-height:", new CSSInfo[] {new CSSInfo("normal",""),new CSSInfo("number",""),new CSSInfo("length",""),new CSSInfo("%",""),new CSSInfo("inherit","")}),
			new CSSValue("vertical-align:", new CSSInfo[] {new CSSInfo("baseline",""),new CSSInfo("sub",""),new CSSInfo("super",""),new CSSInfo("top",""),new CSSInfo("text-top",""),new CSSInfo("middle",""),new CSSInfo("bottom",""),new CSSInfo("text-bottom",""),new CSSInfo("length",""),new CSSInfo("%",""),new CSSInfo("inherit","")}),
			new CSSValue("overflow:", new CSSInfo[] {new CSSInfo("visible","默认值。内容不会被修剪，会呈现在元素框之外。"),new CSSInfo("hidden","内容会被修剪，并且其余内容是不可见的。"),new CSSInfo("scroll","内容会被修剪，但是浏览器会显示滚动条以便查看其余的内容。"),new CSSInfo("auto","如果内容被修剪，则浏览器会显示滚动条以便查看其余的内容。"),new CSSInfo("inherit","规定应该从父元素继承 overflow 属性的值。")}),
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
			new CSSValue("cursor:", new CSSInfo[] {new CSSInfo("auto","默认值"),new CSSInfo("default","默认光标，不考虑上下文，通常是一个箭头"),new CSSInfo("pointer","表示一个链接"),new CSSInfo("help","表示有帮助"),new CSSInfo("progress","进度指示器，表示程序正在执行一些处理"),new CSSInfo("wait","表示程序繁忙，您应该等待程序指向完成"),new CSSInfo("text","表示可以选择的文本"),new CSSInfo("move","表示可以移动鼠标下方的对象"),new CSSInfo("col-resize","表示可以水平调整列的大小"),new CSSInfo("row-resize","表示可以垂直调整行的大小"),new CSSInfo("n-resize","表示对象的边缘可以向上（向北）移动"),new CSSInfo("s-resize","表示对象的边缘可以向下（向南）移动"),new CSSInfo("grab","表示可以抓取（拖动）某些东西"),new CSSInfo("grabbing","表示已经抓取到某些东西"),new CSSInfo("none","不显示光标")}),
			new CSSValue("outline-width:"),
			new CSSValue("outline-color:"),
			new CSSValue("outline-style:"),
			new CSSValue("outline:")
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
