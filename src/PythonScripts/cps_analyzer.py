import sys

logs = open(sys.argv[1], 'r')
msg_count = 0
for _ in logs:
    msg_count += 1
logs.close()
print(msg_count)
