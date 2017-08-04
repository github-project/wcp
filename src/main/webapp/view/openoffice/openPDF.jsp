<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在线打开PDF文件</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>
<body>
	<!--**************   卓正 PageOffice 客户端代码开始    ************************-->
	<script language="javascript" type="text/javascript">
		function AfterDocumentOpened() {
			//alert(document.getElementById("PDFCtrl1").Caption);
		}
		function SetBookmarks() {
			document.getElementById("PDFCtrl1").BookmarksVisible = !document
					.getElementById("PDFCtrl1").BookmarksVisible;
		}
		function SearchText() {
			document.getElementById("PDFCtrl1").SearchText();
		}
		function SearchTextNext() {
			document.getElementById("PDFCtrl1").SearchTextNext();
		}
		function SearchTextPrev() {
			document.getElementById("PDFCtrl1").SearchTextPrev();
		}
		function SetPageReal() {
			document.getElementById("PDFCtrl1").SetPageFit(1);
		}
		function SetPageFit() {
			document.getElementById("PDFCtrl1").SetPageFit(2);
		}
		function SetPageWidth() {
			document.getElementById("PDFCtrl1").SetPageFit(3);
		}
		function Print() {
			document.getElementById("PDFCtrl1").ShowDialog(4);
		}
		function SwitchFullScreen() {
			document.getElementById("PDFCtrl1").FullScreen = !document
					.getElementById("PDFCtrl1").FullScreen;
		}
		function SetPageReal() {
			document.getElementById("PDFCtrl1").SetPageFit(1);
		}
		function SetPageFit() {
			document.getElementById("PDFCtrl1").SetPageFit(2);
		}
		function SetPageWidth() {
			document.getElementById("PDFCtrl1").SetPageFit(3);
		}
		function ZoomIn() {
			document.getElementById("PDFCtrl1").ZoomIn();
		}
		function ZoomOut() {
			document.getElementById("PDFCtrl1").ZoomOut();
		}
		function FirstPage() {
			document.getElementById("PDFCtrl1").GoToFirstPage();
		}
		function PreviousPage() {
			document.getElementById("PDFCtrl1").GoToPreviousPage();
		}
		function NextPage() {
			document.getElementById("PDFCtrl1").GoToNextPage();
		}
		function LastPage() {
			document.getElementById("PDFCtrl1").GoToLastPage();
		}
		function RotateRight() {
			document.getElementById("PDFCtrl1").RotateRight();
		}
		function RotateLeft() {
			document.getElementById("PDFCtrl1").RotateLeft();
		}
	</script>
	<!--**************   卓正 PageOffice 客户端代码结束    ************************-->
	<div style="width: auto; height: 870px;">
		<po:PDFCtrl id="PDFCtrl1" />
	</div>
</body>
</html>