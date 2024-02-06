import socket
import json

client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
server_address = ('localhost', 12345)


msg = input("Enter a message: ")
client_socket.sendto(msg.encode('utf-8'), server_address)

i = input("Enter start index: ")
client_socket.sendto(str(i).encode('utf-8'), server_address)

l = input("Enter a length: ")
client_socket.sendto(str(l).encode('utf-8'), server_address)


data, server_address = client_socket.recvfrom(1024)
response = data.decode('utf-8')

print(response)


client_socket.close()