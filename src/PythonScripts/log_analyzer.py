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
        average_time = register[thread_id][1]
        register[thread_id] = (count + 1, (count * average_time + time_elapsed)/(count + 1))

def update_buffer(thread_id, buffer, index, value, is_producer):
    if is_producer:
        if index in buffer:
            raise Exception("Producer of ID: " + str(thread_id) + " illegally produced value: " 
                            + str(value) + " at index: " + str(index) + " which was not consumed before")
        else:
            buffer[index] = value
    else:
        if index not in buffer:
            raise Exception("Consumer of ID: " + str(thread_id) + " illegally consumed value: " 
                            + str(value) + " at index: " + str(index) + " which was not produced before")
        elif buffer[index] != value:
            raise Exception("Consumer of ID: " + str(thread_id) + " illegally consumed value: " 
                            + str(value) + " at index: " + str(index) + " which should be: " + str(buffer[index]))
        else:
            del buffer[index]

def analyze_line(line):
    is_producer = line[0] == 'P'
    numbers = [int(word) for word in re.split(pattern, line) if word != '']
    thread_id = numbers.pop(0)
    time_elapsed = numbers.pop(-1)
    if is_producer:
        update_register(thread_id, producer_register, time_elapsed)
    else:
        update_register(thread_id, consumer_register, time_elapsed)
    idx_val_pairs = [(numbers[i], numbers[i + 1]) for i in range(0, len(numbers) - 1, 2)]
    for index, value in idx_val_pairs:
        update_buffer(thread_id, buffer, index, value, is_producer)

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
              + str(count) + " times on average and used average scheduler time of : " + str(average_time))
    print("Consumers:")
    for thread_id, (count, average_time) in consumer_register.items():
        print("Consumer of ID: " + str(thread_id) + " fulfilled request " 
          + str(count) + " times on average and used average scheduler time of : " + str(average_time))

main()