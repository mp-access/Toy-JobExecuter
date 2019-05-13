# Example API Calls Rebuilt from the testsubmit.py

## Submit simple python hello world

```BASH
curl --request POST \
  --url http://localhost:4000/jobe/index.php/restapi/runs \
  --header 'accept: application/json' \
  --header 'accept-charset: utf-8' \
  --header 'content-type: application/json' \
  --data '{\n    "run_spec": {\n        "language_id": "python3",\n        "sourcecode": "print(\"Hello world!\")\n",\n        "sourcefilename": "test.py"\n    }\n}'
```

Results:
```JSON
{
  "run_id": null,
  "outcome": 15,
  "cmpinfo": "",
  "stdout": "Hello world!\n",
  "stderr": ""
}
```

## Submit simple python with commandline args

```BASH
curl --request POST \
  --url http://localhost:4000/jobe/index.php/restapi/runs \
  --header 'accept: application/json' \
  --header 'accept-charset: utf-8' \
  --header 'content-type: application/json' \
  --data '{\n    "run_spec": {\n        "language_id": "python3",\n        "sourcecode": "print(input())\nprint(input())\n",\n        "input": "Line1\nLine2\n",\n        "sourcefilename": "test.py"\n    }\n}'
```

Results:
```JSON
{
  "run_id": null,
  "outcome": 15,
  "cmpinfo": "",
  "stdout": "Line1\nLine2\n",
  "stderr": ""
}
```

## Submit simple python with invalid syntax

```BASH
curl --request POST \
  --url http://localhost:4000/jobe/index.php/restapi/runs \
  --header 'accept: application/json' \
  --header 'accept-charset: utf-8' \
  --header 'content-type: application/json' \
  --data '{\n    "run_spec": {\n        "language_id": "python3",\n        "sourcecode": "print(\"Hello world!\"\n",\n        "sourcefilename": "test.py"\n    }\n}'
```

Results:
```JSON
{
  "run_id": null,
  "outcome": 11,
  "cmpinfo": "  File \"test.py\", line 1\n    print(\"Hello world!\"\n                       ^\nSyntaxError: unexpected EOF while parsing\n\n",
  "stdout": "",
  "stderr": ""
}
```


## Submit a file

A file content must be submited base64 encoded.

```PYTHON
msg = "Hello Python File World."
print(msg)
```

`bXNnID0gIkhlbGxvIFB5dGhvbiBGaWxlIFdvcmxkLiIKcHJpbnQobXNnKQ==`

```BASH
curl --request PUT \
  --url http://localhost:4000/jobe/index.php/restapi/files/dd \
  --header 'accept: application/json' \
  --header 'accept-charset: utf-8' \
  --header 'content-type: application/json' \
  --data '{\n    "file_contents": "bXNnID0gIkhlbGxvIFB5dGhvbiBGaWxlIFdvcmxkLiIKcHJpbnQobXNnKQ=="\n}'
```

Results:

 - 204 Status when ok
 - Creates/Updates a file in `/var/www/html/jobe/files` with the given id as file name

 Hints:
  - ATTENTION: Client is responsible for uniqueness of id (otherwise the file on the server will be overwritten.) 

## Execute Exercise Example

__Store files on server:__


```BASH
# script
curl --request PUT \
  --url http://localhost:4000/jobe/index.php/restapi/files/6536939f0097d799ef136ee8325691fe \
  --header 'accept: application/json' \
  --header 'accept-charset: utf-8' \
  --header 'content-type: application/json' \
  --data '{
    "file_contents": "aWYgX19uYW1lX18gPT0gJ19fbWFpbl9fJzoKICAgIGludGVyY29udGluZW50YWxfZmxpZ2h0ID0gSW50ZXJjb250aW5lbnRhbEFpcmNyYWZ0KDUwMCwgIkJvZWluZy03NDciLCAxMDApCiAgICBzaG9ydF9oYXVsX2ZsaWdodCA9IFNob3J0SGF1bEFpcmNyYWZ0KDExMCwgIkFpcmJ1cy1BMjIwIikKICAgIHNob3J0X2hhdWxfZmxpZ2h0MiA9IFNob3J0SGF1bEFpcmNyYWZ0KDg1LCAiQWlyYnVzLUEyMjAiKQoKICAgIGFzc2VydCBzaG9ydF9oYXVsX2ZsaWdodC5nZXRfc2VyaWFsX251bWJlcigpID09IDAKICAgIGFzc2VydCBzaG9ydF9oYXVsX2ZsaWdodDIuZ2V0X3NlcmlhbF9udW1iZXIoKSA9PSAxCgogICAgYXNzZXJ0IHNob3J0X2hhdWxfZmxpZ2h0LmdldF9udW1iZXJfb2ZfcGFzc2VuZ2VycygpID09IDExMAogICAgYXNzZXJ0IHNob3J0X2hhdWxfZmxpZ2h0LmdldF9uYW1lKCkgPT0gIkFpcmJ1cy1BMjIwIgoKICAgIGFzc2VydCBpbnRlcmNvbnRpbmVudGFsX2ZsaWdodC5nZXRfbnVtYmVyX29mX3Bhc3NlbmdlcnMoKSA9PSA1MDAKICAgIGFzc2VydCBpbnRlcmNvbnRpbmVudGFsX2ZsaWdodC5nZXRfbmFtZSgpID09ICJCb2VpbmctNzQ3IgoKICAgIGFzc2VydCBpbnRlcmNvbnRpbmVudGFsX2ZsaWdodC5jYWxjdWxhdGVfYW1vdW50X29mX2Z1ZWwoMTAwMDApID09IDMyNTAwMDAuCiAgICBhc3NlcnQgc2hvcnRfaGF1bF9mbGlnaHQuY2FsY3VsYXRlX2Ftb3VudF9vZl9mdWVsKDI1MCkgPT0gMjc1MC4KCiAgICBhc3NlcnQgaW50ZXJjb250aW5lbnRhbF9mbGlnaHQubWFuaWZlc3QgPT0gIkludGVyY29udGluZW50YWwgZmxpZ2h0IEJvZWluZy03NDc6IHBhc3NlbmdlciBjb3VudCA1MDAsIGNhcmdvIGxvYWQgMTAwIgogICAgYXNzZXJ0IHNob3J0X2hhdWxfZmxpZ2h0Mi5tYW5pZmVzdCA9PSAiU2hvcnQgaGF1bCBmbGlnaHQgc2VyaWFsIG51bWJlciAxLCBuYW1lIEFpcmJ1cy1BMjIwOiBwYXNzZW5nZXIgY291bnQgODUiCgogICAgdG93ZXIgPSBDb250cm9sVG93ZXIoKQogICAgdG93ZXIuYWRkX2FpcmNyYWZ0KGludGVyY29udGluZW50YWxfZmxpZ2h0KQogICAgdG93ZXIuYWRkX2FpcmNyYWZ0KHNob3J0X2hhdWxfZmxpZ2h0KQogICAgdG93ZXIuYWRkX2FpcmNyYWZ0KHNob3J0X2hhdWxfZmxpZ2h0MikKCiAgICBhaXJfdHJhZmZpY19yZXBvcnQgPSB0b3dlci5nZXRfbWFuaWZlc3RzKCkKICAgIGZvciBhaXJjcmFmdCBpbiBhaXJfdHJhZmZpY19yZXBvcnQ6CiAgICAgICAgcHJpbnQoYWlyY3JhZnQpCgogICAgIyBwcmludHM6CiAgICAjIEludGVyY29udGluZW50YWwgZmxpZ2h0IEJvZWluZy03NDc6IHBhc3NlbmdlciBjb3VudCA1MDAsIGNhcmdvIGxvYWQgMTAwCiAgICAjIFNob3J0IGhhdWwgZmxpZ2h0IHNlcmlhbCBudW1iZXIgMCwgbmFtZSBBaXJidXMtQTIyMDogcGFzc2VuZ2VyIGNvdW50IDExMAogICAgIyBTaG9ydCBoYXVsIGZsaWdodCBzZXJpYWwgbnVtYmVyIDEsIG5hbWUgQWlyYnVzLUEyMjA6IHBhc3NlbmdlciBjb3VudCA4NQo="
}'

# testsuite
curl --request PUT \
  --url http://localhost:4000/jobe/index.php/restapi/files/cf60778bbd1186ecfd311cf8cddd1708 \
  --header 'accept: application/json' \
  --header 'accept-charset: utf-8' \
  --header 'content-type: application/json' \
  --data '{
    "file_contents": "ZnJvbSB1bml0dGVzdCBpbXBvcnQgVGVzdENhc2UKCmZyb20gc29sdXRpb25fZmlsZXMudGFza18xIGltcG9ydCBBaXJjcmFmdCwgSW50ZXJjb250aW5lbnRhbEFpcmNyYWZ0LCBTaG9ydEhhdWxBaXJjcmFmdCwgQ29udHJvbFRvd2VyCgpjbGFzcyBUYXNrMVRlc3QoVGVzdENhc2UpOgoKICAgIGRlZiB0ZXN0X2luaGVyaXRhbmNlKHNlbGYpOgogICAgICAgIGludGVyY29udGluZW50YWxfZmxpZ2h0ID0gSW50ZXJjb250aW5lbnRhbEFpcmNyYWZ0KDQwLCAiaW50ZXJjb250aW5lbnRhbCIsIDEwMCkKICAgICAgICBzaG9ydF9oYXVsX2ZsaWdodCA9IFNob3J0SGF1bEFpcmNyYWZ0KDkwLCAic2hvcnQiKQoKICAgICAgICBzZWxmLmFzc2VydFRydWUoaXNpbnN0YW5jZShpbnRlcmNvbnRpbmVudGFsX2ZsaWdodCwgQWlyY3JhZnQpLAogICAgICAgICAgICAgICAgICAgICAgICAiSW50ZXJjb250aW5lbnRhbEFpcmNyYWZ0IHNob3VsZCBpbmhlcml0IGZyb20gQWlyY3JhZnQiKQogICAgICAgIHNlbGYuYXNzZXJ0VHJ1ZShpc2luc3RhbmNlKHNob3J0X2hhdWxfZmxpZ2h0LCBBaXJjcmFmdCksICJTaG9ydEhhdWxBaXJjcmFmdCBzaG91bGQgaW5oZXJpdCBmcm9tIEFpcmNyYWZ0IikKCiAgICBkZWYgdGVzdF9haXJjcmFmdF9nZXRfbmFtZShzZWxmKToKICAgICAgICBhaXJjcmFmdCA9IEludGVyY29udGluZW50YWxBaXJjcmFmdCg0MCwgImludGVyY29udGluZW50YWwiLCAxMDApCiAgICAgICAgc2VsZi5hc3NlcnRFcXVhbChhaXJjcmFmdC5nZXRfbmFtZSgpLCAiaW50ZXJjb250aW5lbnRhbCIpCgogICAgICAgIGFpcmNyYWZ0ID0gU2hvcnRIYXVsQWlyY3JhZnQoOTAsICJzaG9ydCIpCiAgICAgICAgc2VsZi5hc3NlcnRFcXVhbChhaXJjcmFmdC5nZXRfbmFtZSgpLCAic2hvcnQiKQoKICAgIGRlZiB0ZXN0X2FpcmNyYWZ0X2dldF9udW1iZXJfb2ZfcGFzc2VuZ2VycyhzZWxmKToKICAgICAgICBhaXJjcmFmdCA9IEludGVyY29udGluZW50YWxBaXJjcmFmdCg0MCwgImludGVyY29udGluZW50YWwiLCAxMDApCiAgICAgICAgc2VsZi5hc3NlcnRFcXVhbChhaXJjcmFmdC5nZXRfbnVtYmVyX29mX3Bhc3NlbmdlcnMoKSwgNDApCgogICAgICAgIGFpcmNyYWZ0ID0gU2hvcnRIYXVsQWlyY3JhZnQoOTAsICJzaG9ydCIpCiAgICAgICAgc2VsZi5hc3NlcnRFcXVhbChhaXJjcmFmdC5nZXRfbnVtYmVyX29mX3Bhc3NlbmdlcnMoKSwgOTApCgogICAgZGVmIHRlc3RfY2FsY3VsYXRlX2Ftb3VudF9vZl9mdWVsX2ludGVyY29udGluZW50YWwoc2VsZik6CiAgICAgICAgYWlyY3JhZnQgPSBJbnRlcmNvbnRpbmVudGFsQWlyY3JhZnQoNDAsICJpbnRlcmNvbnRpbmVudGFsIiwgMTAwKQogICAgICAgIGZ1ZWwgPSBhaXJjcmFmdC5jYWxjdWxhdGVfYW1vdW50X29mX2Z1ZWwoMTAwMCkKICAgICAgICBzZWxmLmFzc2VydEVxdWFsKGZ1ZWwsIDIxMDAwMCkKCiAgICBkZWYgdGVzdF9nZXRfbWFuaWZlc3RfaW50ZXJjb250aW5lbnRhbChzZWxmKToKICAgICAgICBhaXJjcmFmdCA9IEludGVyY29udGluZW50YWxBaXJjcmFmdCg0MCwgImludGVyY29udGluZW50YWwiLCAxMDApCiAgICAgICAgc2VsZi5hc3NlcnRFcXVhbChhaXJjcmFmdC5tYW5pZmVzdCwKICAgICAgICAgICAgICAgICAgICAgICAgIGYiSW50ZXJjb250aW5lbnRhbCBmbGlnaHQgaW50ZXJjb250aW5lbnRhbDogcGFzc2VuZ2VyIGNvdW50IDQwLCBjYXJnbyBsb2FkIDEwMCIpCgogICAgZGVmIHRlc3RfY2FsY3VsYXRlX2Ftb3VudF9vZl9mdWVsX3Nob3J0X2hhdWwoc2VsZik6CiAgICAgICAgYWlyY3JhZnQgPSBTaG9ydEhhdWxBaXJjcmFmdCg5MCwgInNob3J0IikKICAgICAgICBmdWVsID0gYWlyY3JhZnQuY2FsY3VsYXRlX2Ftb3VudF9vZl9mdWVsKDEwMDApCiAgICAgICAgc2VsZi5hc3NlcnRFcXVhbChmdWVsLCA5MDAwKQoKICAgIGRlZiB0ZXN0X2dldF9tYW5pZmVzdF9zaG9ydF9oYXVsKHNlbGYpOgogICAgICAgIGFpcmNyYWZ0ID0gU2hvcnRIYXVsQWlyY3JhZnQoNDAsICJzaG9ydCIpCiAgICAgICAgc2VyaWFsX251bWJlciA9IGFpcmNyYWZ0LmdldF9zZXJpYWxfbnVtYmVyKCkKICAgICAgICBzZWxmLmFzc2VydEVxdWFsKGFpcmNyYWZ0Lm1hbmlmZXN0LAogICAgICAgICAgICAgICAgICAgICAgICAgZiJTaG9ydCBoYXVsIGZsaWdodCBzZXJpYWwgbnVtYmVyIHtzZXJpYWxfbnVtYmVyfSwgbmFtZSBzaG9ydDogcGFzc2VuZ2VyIGNvdW50IDQwIikKCiAgICBkZWYgdGVzdF9saXN0X2ZsaWdodHMoc2VsZik6CiAgICAgICAgaW50ZXJjb250aW5lbnRhbF9mbGlnaHQgPSBJbnRlcmNvbnRpbmVudGFsQWlyY3JhZnQoNTAwLCAiQm9laW5nLTc0NyIsIDEwMCkKICAgICAgICBzaG9ydF9oYXVsX2ZsaWdodCA9IFNob3J0SGF1bEFpcmNyYWZ0KDExMCwgIkFpcmJ1cy1BMjIwIikKICAgICAgICBzaG9ydF9oYXVsX2ZsaWdodDIgPSBTaG9ydEhhdWxBaXJjcmFmdCg4NSwgIkFpcmJ1cy1BMjIwIikKCiAgICAgICAgdG93ZXIgPSBDb250cm9sVG93ZXIoKQogICAgICAgIHRvd2VyLmFkZF9haXJjcmFmdChpbnRlcmNvbnRpbmVudGFsX2ZsaWdodCkKICAgICAgICB0b3dlci5hZGRfYWlyY3JhZnQoc2hvcnRfaGF1bF9mbGlnaHQpCiAgICAgICAgdG93ZXIuYWRkX2FpcmNyYWZ0KHNob3J0X2hhdWxfZmxpZ2h0MikKCiAgICAgICAgbWFuaWZlc3RzID0gdG93ZXIuZ2V0X21hbmlmZXN0cygpCiAgICAgICAgc2VsZi5hc3NlcnRFcXVhbChtYW5pZmVzdHMsIFsKICAgICAgICAgICAgIkludGVyY29udGluZW50YWwgZmxpZ2h0IEJvZWluZy03NDc6IHBhc3NlbmdlciBjb3VudCA1MDAsIGNhcmdvIGxvYWQgMTAwIiwKICAgICAgICAgICAgZiJTaG9ydCBoYXVsIGZsaWdodCBzZXJpYWwgbnVtYmVyIHtzaG9ydF9oYXVsX2ZsaWdodC5nZXRfc2VyaWFsX251bWJlcigpfSwgbmFtZSBBaXJidXMtQTIyMDogcGFzc2VuZ2VyIGNvdW50IDExMCIsCiAgICAgICAgICAgIGYiU2hvcnQgaGF1bCBmbGlnaHQgc2VyaWFsIG51bWJlciB7c2hvcnRfaGF1bF9mbGlnaHQyLmdldF9zZXJpYWxfbnVtYmVyKCl9LCBuYW1lIEFpcmJ1cy1BMjIwOiBwYXNzZW5nZXIgY291bnQgODUiXSkK"
}'

#solution
curl --request PUT \
  --url http://localhost:4000/jobe/index.php/restapi/files/f39f626d7a91817ba2cddf917cc78228 \
  --header 'accept: application/json' \
  --header 'accept-charset: utf-8' \
  --header 'content-type: application/json' \
  --data '{
    "file_contents": "Y2xhc3MgQWlyY3JhZnQob2JqZWN0KToKCiAgICBkZWYgX19pbml0X18oc2VsZiwgbnVtYmVyX29mX3Bhc3NlbmdlcnMsIG5hbWUpOgogICAgICAgIHNlbGYuX251bWJlcl9vZl9wYXNzZW5nZXIgPSBudW1iZXJfb2ZfcGFzc2VuZ2VycwogICAgICAgIHNlbGYuX25hbWUgPSBuYW1lCgogICAgZGVmIGdldF9udW1iZXJfb2ZfcGFzc2VuZ2VycyhzZWxmKToKICAgICAgICByZXR1cm4gc2VsZi5fbnVtYmVyX29mX3Bhc3NlbmdlcgoKICAgIGRlZiBnZXRfbmFtZShzZWxmKToKICAgICAgICByZXR1cm4gc2VsZi5fbmFtZQoKCmNsYXNzIEludGVyY29udGluZW50YWxBaXJjcmFmdChBaXJjcmFmdCk6CiAgICBjb25zdW1wdGlvbl9wZXJfa21fcGVyX3Bhc3NlbmdlciA9IDAuMjUKCiAgICBjb25zdW1wdGlvbl9wZXJfa21fcGVyX3Rvbm5lX2NhcmdvID0gMgoKICAgIGRlZiBfX2luaXRfXyhzZWxmLCBudW1iZXJfb2ZfcGFzc2VuZ2VycywgbmFtZSwgY2FyZ29fbG9hZCk6CiAgICAgICAgQWlyY3JhZnQuX19pbml0X18oc2VsZiwgbnVtYmVyX29mX3Bhc3NlbmdlcnMsIG5hbWUpCiAgICAgICAgc2VsZi5fX2NhcmdvX2xvYWQgPSBjYXJnb19sb2FkCgogICAgZGVmIGNhbGN1bGF0ZV9hbW91bnRfb2ZfZnVlbChzZWxmLCBrbSk6CiAgICAgICAgcGFzc2VuZ2VyX2NvbnN1bXB0aW9uID0gSW50ZXJjb250aW5lbnRhbEFpcmNyYWZ0LmNvbnN1bXB0aW9uX3Blcl9rbV9wZXJfcGFzc2VuZ2VyICogc2VsZi5fbnVtYmVyX29mX3Bhc3NlbmdlciAqIGttCiAgICAgICAgY2FyZ29fY29uc3VtcHRpb24gPSBJbnRlcmNvbnRpbmVudGFsQWlyY3JhZnQuY29uc3VtcHRpb25fcGVyX2ttX3Blcl90b25uZV9jYXJnbyAqIHNlbGYuX19jYXJnb19sb2FkICoga20KICAgICAgICByZXR1cm4gcGFzc2VuZ2VyX2NvbnN1bXB0aW9uICsgY2FyZ29fY29uc3VtcHRpb24KCiAgICBAcHJvcGVydHkKICAgIGRlZiBtYW5pZmVzdChzZWxmKToKICAgICAgICByZXR1cm4gZiJJbnRlcmNvbnRpbmVudGFsIGZsaWdodCB7c2VsZi5fbmFtZX06IHBhc3NlbmdlciBjb3VudCB7c2VsZi5fbnVtYmVyX29mX3Bhc3Nlbmdlcn0sIGNhcmdvIGxvYWQge3NlbGYuX19jYXJnb19sb2FkfSIKCgpjbGFzcyBTaG9ydEhhdWxBaXJjcmFmdChBaXJjcmFmdCk6CiAgICBjb25zdW1wdGlvbl9wZXJfa21fcGVyX3Bhc3NlbmdlciA9IDAuMQoKICAgIHNlcmlhbF9udW1iZXJfY291bnRlciA9IDAKCiAgICBkZWYgX19pbml0X18oc2VsZiwgbnVtYmVyX29mX3Bhc3NlbmdlcnMsIG5hbWUpOgogICAgICAgIEFpcmNyYWZ0Ll9faW5pdF9fKHNlbGYsIG51bWJlcl9vZl9wYXNzZW5nZXJzLCBuYW1lKQogICAgICAgIHNlbGYuX19zZXJpYWxfbnVtYmVyID0gU2hvcnRIYXVsQWlyY3JhZnQuY29uc3RydWN0X25ld19zZXJpYWxfbnVtYmVyKCkKCiAgICBkZWYgY2FsY3VsYXRlX2Ftb3VudF9vZl9mdWVsKHNlbGYsIGttKToKICAgICAgICByZXR1cm4gU2hvcnRIYXVsQWlyY3JhZnQuY29uc3VtcHRpb25fcGVyX2ttX3Blcl9wYXNzZW5nZXIgKiBrbSAqIHNlbGYuZ2V0X251bWJlcl9vZl9wYXNzZW5nZXJzKCkKCiAgICBkZWYgZ2V0X3NlcmlhbF9udW1iZXIoc2VsZik6CiAgICAgICAgcmV0dXJuIHNlbGYuX19zZXJpYWxfbnVtYmVyCgogICAgQHByb3BlcnR5CiAgICBkZWYgbWFuaWZlc3Qoc2VsZik6CiAgICAgICAgcmV0dXJuIGYiU2hvcnQgaGF1bCBmbGlnaHQgc2VyaWFsIG51bWJlciB7c2VsZi5fX3NlcmlhbF9udW1iZXJ9LCBuYW1lIHtzZWxmLl9uYW1lfTogcGFzc2VuZ2VyIGNvdW50IHtzZWxmLl9udW1iZXJfb2ZfcGFzc2VuZ2VyfSIKCiAgICBAc3RhdGljbWV0aG9kCiAgICBkZWYgY29uc3RydWN0X25ld19zZXJpYWxfbnVtYmVyKCk6CiAgICAgICAgc2VyaWFsX251bWJlciA9IFNob3J0SGF1bEFpcmNyYWZ0LnNlcmlhbF9udW1iZXJfY291bnRlcgogICAgICAgIFNob3J0SGF1bEFpcmNyYWZ0LnNlcmlhbF9udW1iZXJfY291bnRlciArPSAxCiAgICAgICAgcmV0dXJuIHNlcmlhbF9udW1iZXIKCgpjbGFzcyBDb250cm9sVG93ZXIob2JqZWN0KToKCiAgICBkZWYgX19pbml0X18oc2VsZik6CiAgICAgICAgc2VsZi5fX3BsYW5lcyA9IFtdCgogICAgZGVmIGFkZF9haXJjcmFmdChzZWxmLCBhaXJjcmFmdCk6CiAgICAgICAgc2VsZi5fX3BsYW5lcy5hcHBlbmQoYWlyY3JhZnQpCgogICAgZGVmIGdldF9tYW5pZmVzdHMoc2VsZik6CiAgICAgICAgcmV0dXJuIFthaXJjcmFmdC5tYW5pZmVzdCBmb3IgYWlyY3JhZnQgaW4gc2VsZi5fX3BsYW5lc10KCgppZiBfX25hbWVfXyA9PSAnX19tYWluX18nOgogICAgaW50ZXJjb250aW5lbnRhbF9mbGlnaHQgPSBJbnRlcmNvbnRpbmVudGFsQWlyY3JhZnQoNTAwLCAiQm9laW5nLTc0NyIsIDEwMCkKICAgIHNob3J0X2hhdWxfZmxpZ2h0ID0gU2hvcnRIYXVsQWlyY3JhZnQoMTEwLCAiQWlyYnVzLUEyMjAiKQogICAgc2hvcnRfaGF1bF9mbGlnaHQyID0gU2hvcnRIYXVsQWlyY3JhZnQoODUsICJBaXJidXMtQTIyMCIpCgogICAgYXNzZXJ0IHNob3J0X2hhdWxfZmxpZ2h0LmdldF9zZXJpYWxfbnVtYmVyKCkgPT0gMAogICAgYXNzZXJ0IHNob3J0X2hhdWxfZmxpZ2h0Mi5nZXRfc2VyaWFsX251bWJlcigpID09IDEKCiAgICBhc3NlcnQgc2hvcnRfaGF1bF9mbGlnaHQuZ2V0X251bWJlcl9vZl9wYXNzZW5nZXJzKCkgPT0gMTEwCiAgICBhc3NlcnQgc2hvcnRfaGF1bF9mbGlnaHQuZ2V0X25hbWUoKSA9PSAiQWlyYnVzLUEyMjAiCgogICAgYXNzZXJ0IGludGVyY29udGluZW50YWxfZmxpZ2h0LmdldF9udW1iZXJfb2ZfcGFzc2VuZ2VycygpID09IDUwMAogICAgYXNzZXJ0IGludGVyY29udGluZW50YWxfZmxpZ2h0LmdldF9uYW1lKCkgPT0gIkJvZWluZy03NDciCgogICAgYXNzZXJ0IGludGVyY29udGluZW50YWxfZmxpZ2h0LmNhbGN1bGF0ZV9hbW91bnRfb2ZfZnVlbCgxMDAwMCkgPT0gMzI1MDAwMC4KICAgIGFzc2VydCBzaG9ydF9oYXVsX2ZsaWdodC5jYWxjdWxhdGVfYW1vdW50X29mX2Z1ZWwoMjUwKSA9PSAyNzUwLgoKICAgIGFzc2VydCBpbnRlcmNvbnRpbmVudGFsX2ZsaWdodC5tYW5pZmVzdCA9PSAiSW50ZXJjb250aW5lbnRhbCBmbGlnaHQgQm9laW5nLTc0NzogcGFzc2VuZ2VyIGNvdW50IDUwMCwgY2FyZ28gbG9hZCAxMDAiCiAgICBhc3NlcnQgc2hvcnRfaGF1bF9mbGlnaHQyLm1hbmlmZXN0ID09ICJTaG9ydCBoYXVsIGZsaWdodCBzZXJpYWwgbnVtYmVyIDEsIG5hbWUgQWlyYnVzLUEyMjA6IHBhc3NlbmdlciBjb3VudCA4NSIKCiAgICB0b3dlciA9IENvbnRyb2xUb3dlcigpCiAgICB0b3dlci5hZGRfYWlyY3JhZnQoaW50ZXJjb250aW5lbnRhbF9mbGlnaHQpCiAgICB0b3dlci5hZGRfYWlyY3JhZnQoc2hvcnRfaGF1bF9mbGlnaHQpCiAgICB0b3dlci5hZGRfYWlyY3JhZnQoc2hvcnRfaGF1bF9mbGlnaHQyKQoKICAgIGFpcl90cmFmZmljX3JlcG9ydCA9IHRvd2VyLmdldF9tYW5pZmVzdHMoKQogICAgZm9yIGFpcmNyYWZ0IGluIGFpcl90cmFmZmljX3JlcG9ydDoKICAgICAgICBwcmludChhaXJjcmFmdCkKCiAgICAjIHByaW50czoKICAgICMgSW50ZXJjb250aW5lbnRhbCBmbGlnaHQgQm9laW5nLTc0NzogcGFzc2VuZ2VyIGNvdW50IDUwMCwgY2FyZ28gbG9hZCAxMDAKICAgICMgU2hvcnQgaGF1bCBmbGlnaHQgc2VyaWFsIG51bWJlciAwLCBuYW1lIEFpcmJ1cy1BMjIwOiBwYXNzZW5nZXIgY291bnQgMTEwCiAgICAjIFNob3J0IGhhdWwgZmxpZ2h0IHNlcmlhbCBudW1iZXIgMSwgbmFtZSBBaXJidXMtQTIyMDogcGFzc2VuZ2VyIGNvdW50IDg1Cg=="
}'
```

__Execute Testsuite:__


```BASH
curl --request POST \
  --url http://localhost:4000/jobe/index.php/restapi/runs \
  --header 'accept: application/json' \
  --header 'accept-charset: utf-8' \
  --header 'content-type: application/json' \
  --data '{
    "run_spec": {
        "language_id": "python3",
        "file_list": [
            [
                "f39f626d7a91817ba2cddf917cc78228",
                "solutioncode.py",
								true
            ]
        ],
			  "sourcecode": "import unittest \n\nfrom solutioncode import Aircraft, IntercontinentalAircraft, ShortHaulAircraft, ControlTower\n\n\nclass Task1Test(unittest.TestCase):\n\n    def test_inheritance(self):\n        intercontinental_flight = IntercontinentalAircraft(40, \"intercontinental\", 100)\n        short_haul_flight = ShortHaulAircraft(90, \"short\")\n\n        self.assertTrue(isinstance(intercontinental_flight, Aircraft),\n                        \"IntercontinentalAircraft should inherit from Aircraft\")\n        self.assertTrue(isinstance(short_haul_flight, Aircraft), \"ShortHaulAircraft should inherit from Aircraft\")\n\n    def test_aircraft_get_name(self):\n        aircraft = IntercontinentalAircraft(40, \"intercontinental\", 100)\n        self.assertEqual(aircraft.get_name(), \"intercontinental\")\n\n        aircraft = ShortHaulAircraft(90, \"short\")\n        self.assertEqual(aircraft.get_name(), \"short\")\n\n    def test_aircraft_get_number_of_passengers(self):\n        aircraft = IntercontinentalAircraft(40, \"intercontinental\", 100)\n        self.assertEqual(aircraft.get_number_of_passengers(), 40)\n\n        aircraft = ShortHaulAircraft(90, \"short\")\n        self.assertEqual(aircraft.get_number_of_passengers(), 90)\n\n    def test_calculate_amount_of_fuel_intercontinental(self):\n        aircraft = IntercontinentalAircraft(40, \"intercontinental\", 100)\n        fuel = aircraft.calculate_amount_of_fuel(1000)\n        self.assertEqual(fuel, 210000)\n\n    def test_get_manifest_intercontinental(self):\n        aircraft = IntercontinentalAircraft(40, \"intercontinental\", 100)\n        self.assertEqual(aircraft.manifest,\n                        f\"Intercontinental flight intercontinental: passenger count 40, cargo load 100\")\n\n    def test_calculate_amount_of_fuel_short_haul(self):\n        aircraft = ShortHaulAircraft(90, \"short\")\n        fuel = aircraft.calculate_amount_of_fuel(1000)\n        self.assertEqual(fuel, 9000)\n\n    def test_get_manifest_short_haul(self):\n        aircraft = ShortHaulAircraft(40, \"short\")\n        serial_number = aircraft.get_serial_number()\n        self.assertEqual(aircraft.manifest,\n                        f\"Short haul flight serial number {serial_number}, name short: passenger count 40\")\n\n    def test_list_flights(self):\n        intercontinental_flight = IntercontinentalAircraft(500, \"Boeing-747\", 100)\n        short_haul_flight = ShortHaulAircraft(110, \"Airbus-A220\")\n        short_haul_flight2 = ShortHaulAircraft(85, \"Airbus-A220\")\n\n        tower = ControlTower()\n        tower.add_aircraft(intercontinental_flight)\n        tower.add_aircraft(short_haul_flight)\n        tower.add_aircraft(short_haul_flight2)\n\n        manifests = tower.get_manifests()\n        self.assertEqual(manifests, [\n            \"Intercontinental flight Boeing-747: passenger count 500, cargo load 100\",\n            f\"Short haul flight serial number {short_haul_flight.get_serial_number()}, name Airbus-A220: passenger count 110\",\n            f\"Short haul flight serial number {short_haul_flight2.get_serial_number()}, name Airbus-A220: passenger count 85\"])\n\nif __name__ == \"__main__\":\n    unittest.main()\n",
        "sourcefilename": "testsuite.py",
				"debug" : true
    }
}'
```


{
    "run_spec": {
        "comment": "ython3 program with support files",
        "language_id": "python3",
        "files": [
            [
                "randomid012979ddddxxx",
                "VGhlIGZpcnN0IGZpbGVcbkxpbmUgMg=="
            ]
        ],
        "sourcecode": "print(open('file1').read())\n",
        "file_list": [
            [
                "randomid012979ddddxxx",
                "file1"
            ]
        ],
        "sourcefilename": "test.py"
    }
}

{
    "run_spec": {
        "language_id": "python3",
        "file_list": [
            [
                "f39f626d7a91817ba2cddf917cc78228",
                "solutioncode.py",
								true
            ]
        ],
			  "sourcecode": "print(\"Hello world!\")\n",
        "sourcefilename": "testsuite.py",
				"debug" : true
    }
}