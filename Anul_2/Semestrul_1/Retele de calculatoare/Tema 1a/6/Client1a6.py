import socket
import json

def main():
    c = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s_a = ('127.0.0.1', 1234)

    c.connect(s_a)

    sir = input("Enter string: ")
    c.send(sir.encode())

    ch = input("Character: ");
    c.send(ch.encode())

    data = c.recv(1024)
    json_data = json.loads(data.decode())

    print(json_data)

    c.close()

if __name__ == "__main__":
    main()