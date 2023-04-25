const express = require('express');
const path = require('path');
const app = express();
const router = express.Router();

router.use(express.static(path.join(__dirname, 'build')));

router.get('/*', function(req, res) {
  res.sendFile(path.join(__dirname, 'build', 'index.html'));
});

app.use('/', router);
app.listen(3000);