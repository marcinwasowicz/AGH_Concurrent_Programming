import sys

producers_entry_counts = {}
consumers_entry_counts = {}

def analyze_producer_line(line):
    numbers = [int(word) for word in line.split() if word.isdigit()]
    if numbers[0] not in producers_entry_counts:
        producers_entry_counts[numbers[0]] = 0
    producers_entry_counts[numbers[0]] += 1

def analyze_consumer_line(line):
    numbers = [int(word) for word in line.split() if word.isdigit()]
    if numbers[0] not in consumers_entry_counts:
        consumers_entry_counts[numbers[0]] = 0
    consumers_entry_counts[numbers[0]] += 1

file = open(sys.argv[1], 'r')
for line in file:
    line = line.rstrip()
    if line[0] == 'P':
        analyze_producer_line(line)
    else:
        analyze_consumer_line(line)

print("Consumers: \n")
print(consumers_entry_counts)
print("Producers: \n")
print(producers_entry_counts)
