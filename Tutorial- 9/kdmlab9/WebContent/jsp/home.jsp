<script language="javascript" type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
 <script  language="javascript" type="text/javascript"  src="../js/jquery.min.js"></script> 



<form enctype="multipart/form-data" action="./mn" method="POST">
Please Upload the File to Execute Text Runner
<br>
<br><input type="file" name="sonename">
<br>
            <br>
<input type="submit">
<br>
<br>
</form>
<%if(request.getAttribute("output") !=null){ %>
<br>
<br>
<p>Out Put from Text Runner :<br> <%=request.getAttribute("output")  %></p>
<%} %>
