<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Find Leafs of Root NODE NAME</title>
  <link type="text/css" rel="stylesheet" href="./css/global.css">
  <script type="text/javascript" src="./scripts/global.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/_lib/jquery.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/_lib/jquery.hotkeys.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/jquery.cookie.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/jquery.jstree.js"></script>
  <script type="text/javascript" src="./scripts/jsTree.v.1.0rc2/jquery.jstree.extra.js"></script>
</head>

<body>
  <form method="post" action="listbyrootname">
    <fieldset>
      <legend>Find Leafs of Root NODE NAME</legend>
      <p>Enter a Root NODE NAME (EMAPA ID).</p>

      <label for="rootName">ROOT NAME <span class="required">*</span></label>
      <input type="text" id="rootName" name="rootName" value="${fn:escapeXml(leaf.rootName)}" size="15" maxlength="15" ${form.success ? 'disabled="disabled"' : ''}>
      <span class="error">${form.messages.rootName}</span>
      <br/>

      <input type="submit" value="list" class="withoutLabel" ${form.success ? 'disabled="disabled"' : ''}>
      <br />

      <p><a href="index.html">BACK</a></p>

      <p class="${form.success ? 'success' : 'error'}">${form.messages.result}</p>
    
      <table>
        <tr>
          <th>OID</th>
          <th>-</th>
          <th>Root Name</th>
          <th>Root Description</th>
          <th>Root Start</th>
          <th>Root End</th>
          <th>-</th>
          <th>Child Oid</th>
          <th>-</th>
          <th>Child Id</th>
          <th>Child Name</th>
          <th>Child Description</th>
          <th>Child Start</th>
          <th>Child End</th>
          <th>-</th>
          <th>Grand Child Id</th>
          <th>Grand Child Name</th>
          <th>Grand Child Description</th>
        </tr>
        <c:forEach items="${leafs}" var="leaf" varStatus="loop">
          <tr>
            <td>${leaf.rootOid}</td>
            <td>-</td>
            <td>${fn:escapeXml(leaf.rootName)}</td>
            <td>${fn:escapeXml(leaf.rootDescription)}</td>
            <td>${fn:escapeXml(leaf.rootStart)}</td>
            <td>${fn:escapeXml(leaf.rootEnd)}</td>
            <td>-</td>
            <td>${fn:escapeXml(leaf.childOid)}</td>
            <td>-</td>
            <td>${fn:escapeXml(leaf.childId)}</td>
            <td>${fn:escapeXml(leaf.childName)}</td>
            <td>${fn:escapeXml(leaf.childDescription)}</td>
            <td>${fn:escapeXml(leaf.childStart)}</td>
            <td>${fn:escapeXml(leaf.childEnd)}</td>
            <td>-</td>
            <td>${fn:escapeXml(leaf.grandChildId)}</td>
            <td>${fn:escapeXml(leaf.grandChildName)}</td>
            <td>${fn:escapeXml(leaf.grandChildDescription)}</td>
          </tr>
        </c:forEach>
      </table>
      
    </fieldset>
  </form>
  
  <script type="text/javascript">
    setHighlight('${form != null ? form.highlight : ''}');
    setFocus('${form != null ? form.focus : 'id'}');
  </script>
  
  
  
</body>

</html>
