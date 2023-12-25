import os
import socket
import json

def main():
    c = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_address = ('127.0.0.1', 1234)

    c.connect(server_address)

    array1 = [int(i) for i in input("First array: ").split()]
    json_array1 = json.dumps(array1)
    c.send(json_array1.encode())

    array2 = [int(i) for i in input("Second array: ").split()]
    json_array2 = json.dumps(array2)
    c.send(json_array2.encode())

    rez = c.recv(1024)
    json_rez = json.loads(rez.decode())

    print(rez)

    c.close()

if __name__ == "__main__":
    main()