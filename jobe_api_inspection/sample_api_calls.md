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