<html>
<head>
    <title>Create a new blog</title>
</head>
<body>
<h2>Create a Support Ticket</h2>
<form method="POST" action="Form" enctype="multipart/form-data">
    <input type="hidden" name="action" value="create">
    Name:<br>
    <input type="text" name="Name"><br><br>
    Subject:<br>
    <input type="text" name="Subject"><br><br>
    Body:
    <textarea name="body" rows="25" cols="100"></textarea><br><br>
    <b>Attachment</b><br>
    <input type="file" name="file1"><br><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>
