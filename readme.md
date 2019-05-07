Toy Job Executer Project

JOBE (Job Engine) https://github.com/trampgeek/jobe

Recomended to run on a Ubuntu 18.04 Server (There is a 3th party docker image, but it is mentioned that the docker image is not used in production yet.).

install packages:
```BASH
sudo apt install apache2 php libapache2-mod-php php-cli\
    php-mbstring git python3 build-essential \
    python3-pip fp-compiler pylint3 acl sqlite3
```

- start docker: `systemctl start docker`

run docker image: `docker run -d -p 4000:80 --name jobe trampgeek/jobeinabox:latest`

http://localhost:4000/jobe/index.php/restapi/languages

docker run trampgeek/jobeinabox:latest


docker start jobe 

docker stop jobe

docker exec -it jobe bash



docker image ls

docker container ls --all

### Copy stuff

docker cp foo.txt mycontainer:/foo.txt
docker cp mycontainer:/foo.txt foo.txt

docker cp Workspace/MasterProject/Toy-JobExecuter/notes/my_testsubmit.py cc608373f902:/var/www/html/jobe/my_testsubmit.py
docker cp cc608373f902:/var/www/html/jobe/my_testsubmit.log ~/Workspace/MasterProject/Toy-JobExecuter/notes/my_testsubmit.log

## Jobe dirs

/home/jobe/runs
/var/log/jobe/
/var/www/html/jobe/files

{
    'comment': 'Valid Python3',
    'language_id': 'python3',
    'sourcecode': r'''print("Hello world!")''',
    'sourcefilename': 'test.py',
    'expect': { 'outcome': 15, 'stdout': 'Hello world!\n' }
}


run testsubmits.

cd /var/www/html/jobe
python3 testsubmit.py 

