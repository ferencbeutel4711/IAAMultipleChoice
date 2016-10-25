<#assign currentPage = "home">

<html>
<head>
    <title>Registration</title>
<#include "/templates/frameHeadImports.ftl">
    <link rel="stylesheet" href="/static/css/home.css">
</head>
<body>
<#include "/templates/frameHeader.ftl">

<div class="row">
    <div class="col-xs-12">
    <#if Session?? && Session.user??>
        <p><@s.text name="home.loggedIn.welcome"/>, ${name}</p>

        <#if Session?? && Session.user.getClass()?contains("Student")>
            <p><@s.text name="home.loggedIn.infoStudent"/></p>
        </#if>
        <#if Session?? && Session.user.getClass()?contains("Lecturer")>
            <p><@s.text name="home.loggedIn.infoLecturer"/></p>
        </#if>
    <#else>
        <p><@s.text name="home.notLoggedIn.info"/></p>
    </#if>

    </div>
</div>
<#if Session?? && Session.user??>
<div class="seminarList">

    <div class="row seminarListItem seminarListHeader">
        <div class="col-xs-2 seminarListItemEntry">Seminar Name</div>
        <div class="col-xs-4 seminarListItemEntry">Begindate - Enddate</div>
    </div>
<#list seminarList as seminar>
    <div class="row seminarListItem" data-id="${seminar.seminarId}">
        <div class="col-xs-2 seminarListItemEntry">
            <span>${seminar.name}</span>
        </div>
        <div class="col-xs-4 seminarListItemEntry">
            <span>${seminar.beginDate} - ${seminar.endDate}</span>
        </div>
    </div>
</#list>
    </div>
</#if>
<#include "/templates/frameFooter.ftl">
</body>
</html>