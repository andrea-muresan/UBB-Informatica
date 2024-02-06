import socket
import json
from datetime import datetime

client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
server_address = ('localhost', 12345)


msg = input("msg: ")
client_socket.sendto(str(msg).encode('utf-8'), server_address)


seconds, server_address = client_socket.recvfrom(1024)
seconds = int(seconds.decode('utf-8'))
print("seconds = " + str(seconds))


current_datetime = datetime.utcfromtimestamp(seconds)
formatted_date = current_datetime.strftime('%Y-%m-%d %H:%M:%S UTC')
print("Formatted Date and Time:", formatted_date)

client_socket.close()