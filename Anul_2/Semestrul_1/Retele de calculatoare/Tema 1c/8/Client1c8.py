import socket
import json

client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
server_address = ('localhost', 12345)


a = input("a = ")
client_socket.sendto(str(a).encode('utf-8'), server_address)

b = input("b = ")
client_socket.sendto(str(b).encode('utf-8'), server_address)

cmmdc, server_address = client_socket.recvfrom(1024)
cmmdc = cmmdc.decode('utf-8')
print("cmmdc = " + cmmdc)

cmmmc, server_address = client_socket.recvfrom(1024)
cmmmc = cmmmc.decode('utf-8')
print("cmmmc = " + cmmmc)


client_socket.close()