import sys
import re

pattern = '[a-zA-Z{=}:, \n]+'

producer_register = {}
consumer_register = {}
buffer = {}

def update_register(thread_id, register, time_elapsed):
    if thread_id not in register:
        register[thread_id] = (1, time_elapsed)
    else:
        count = register[thread_id][0]
        total_time = register[thread_id][1]
        register[thread_id] = (count + 1, total_time + time_elapsed)

def analyze_line(line):
    is_producer = line[0] == 'P'
    numbers = [int(word) for word in re.split(pattern, line) if word != '']
    thread_id = numbers.pop(0)
    time_elapsed = numbers.pop(-1)
    if is_producer:
        update_register(thread_id, producer_register, time_elapsed)
    else:
        update_register(thread_id, consumer_register, time_elapsed)

def main():
    if len(sys.argv) != 2:
        raise Exception("Invalid Arguments")
    else:
        path = sys.argv[1]
        file = open(path, 'r')
        for line in file:
           analyze_line(line.rstrip())

    print("Analysis found no errors, raport: ")
    print("Producers:")
    for thread_id, (count, average_time) in producer_register.items():
        print("Producer of ID: " + str(thread_id) + " fulfilled request " 
              + str(count) + " times")
    print("Consumers:")
    for thread_id, (count, average_time) in consumer_register.items():
        print("Consumer of ID: " + str(thread_id) + " fulfilled request " 
          + str(count) + " times")

main()