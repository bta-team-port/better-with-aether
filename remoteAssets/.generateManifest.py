from pathlib import Path
import json
import datetime
import hashlib

input_dir = Path(__file__).parent

work_queue: list[Path] = list()

for dir, subdirs, files in (input_dir/"aether").walk():
    for file in files:
        work_queue.append(dir/file)

index = list()
for file in work_queue:
    stat = file.stat()

    with file.open('rb') as handle:
        md5 = hashlib.md5(handle.read()).hexdigest()

    index.append({
        "Key": str(file.absolute()).replace(str(input_dir.absolute()), '').replace("\\", '/')[1:],
        "LastModified": datetime.datetime.fromtimestamp(stat.st_mtime).strftime("%Y-%m-%dT%H:%M:%S.000Z"),
        "Size": stat.st_size,
        "MD5": md5,
    })

with (input_dir/"index.json").open('wb') as handle:
    contents = json.dumps(index, indent=3)
    handle.write(contents.encode())
