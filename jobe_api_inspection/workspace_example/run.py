import zipfile
import os

zip_ref = zipfile.ZipFile("workspace_snapshot.zip", 'r')
zip_ref.extractall(".")
zip_ref.close()

os.system('python3 -m unittest test/*.py -v')