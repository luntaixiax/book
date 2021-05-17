<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: luntaixia
  Date: 2021/5/15
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">

    $(function () {
        $("#searchPageBtn").click(function () {
            var pageNo = $("#pn_input").val();
            var pageTotal = ${requestScope.page.pageTotal};
            if(pageNo < 1 || pageNo > pageTotal ){
                alert("Invalid Page Number!");
                return false;
            }
            else{
                location.href = "${pageScope.basePath}${requestScope.page.url}&pageNo=" + pageNo;
            }
        })
    });

</script>

<div id="page_nav">
    <%--define variables--%>
    <c:set var="pageNo" value="${requestScope.page.pageNo}"/>
    <c:set var="pageTotal" value="${requestScope.page.pageTotal}"/>
    <c:set var="pageTotalCount" value="${requestScope.page.pageTotalCount}"/>
    <c:set var="pageUrl" value="${requestScope.page.url}"/>

    <%--[prev] and [index], only shown if pageNo > 1--%>
    <c:if test="${pageNo > 1}">
        <a href="${ pageUrl }&pageNo=1">Front</a>
        <a href="${ pageUrl }&pageNo=${pageNo - 1}">Prev</a>
    </c:if>

    <%--start printing page buttons--%>
    <c:choose>
        <%-- Scenario1: if total page <= 5, will only show 1,2,..., pageTotal       --%>
        <c:when test="${pageTotal <= 5}">
            <c:set var="begin" value="1"/>
            <c:set var="end" value="${pageTotal}"/>
        </c:when>

        <%-- Scenario2: if total page > 5, have 3 sub-scenarios   --%>
        <c:when test="${pageTotal > 5}">
            <c:choose>

                <%-- Scenario2.1: if current page (pageNo) in {1,2,3}, will show 1,2,3,4,5   --%>
                <c:when test="${pageNo <= 3}">
                    <c:set var="begin" value="1"/>
                    <c:set var="end" value="5"/>
                </c:when>

                <%-- Scenario2.2: if current page (pageNo) in {pageTotal-2, pageTotal-1, pageTotal}, will show pageTotal-4, pageTotal-3, pageTotal-2, pageTotal-1, pageTotal   --%>
                <c:when test="${pageNo >= pageTotal - 2}">
                    <c:set var="begin" value="${pageTotal - 4}"/>
                    <c:set var="end" value="${pageTotal}"/>
                </c:when>

                <%-- Scenario2.3: if current page (pageNo) in {4, 5,..., pageTotal-3}, will show pageNo-2, pageNo-1, pageNo, pageNo+1, pageNo+2   --%>
                <c:otherwise>
                    <c:set var="begin" value="${pageNo - 2}"/>
                    <c:set var="end" value="${pageNo + 2}"/>
                </c:otherwise>

            </c:choose>
        </c:when>
    </c:choose>

    <%--    printing the tags--%>
    <c:forEach begin="${begin}" end="${end}" var="i">
        <c:if test="${i == pageNo}">
            【${i}】
        </c:if>
        <c:if test="${i != pageNo}">
            <a href="${ pageUrl }&pageNo=${i}">${i}</a>
        </c:if>
    </c:forEach>

    <%--[next] and [Final], only shown if pageNo < pageTotal--%>
    <c:if test="${pageNo < pageTotal}">
        <a href="${ pageUrl }&pageNo=${pageNo + 1}">Next</a>
        <a href="${ pageUrl }&pageNo=${pageTotal}">End</a>
    </c:if>


    <%--pageInput field, navigate to specified page--%>
    &nbsp;&nbsp;
    ( ${ pageTotal } pages, ${ pageTotalCount } records)
    &nbsp;&nbsp;&nbsp;&nbsp;
    jump to <input value="${param.pageNo}" name="pn" id="pn_input"/>
    <input id="searchPageBtn" type="button" value="OK">
</div>
