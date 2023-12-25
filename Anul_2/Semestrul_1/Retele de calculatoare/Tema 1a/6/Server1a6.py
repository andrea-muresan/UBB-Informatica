import os
import socket
import json

def server_client(c):
    sir = c.recv(1024).decode()
    ch = c.recv(1).decode()

    rez = [index for index, el in enumerate(sir) if el == ch]
    json_data = json.dumps(rez)
    c.send(json_data.encode())

    c.close()
    os._exit(0)

def main():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s_a = ('127.0.0.1', 1234)

    s.bind(s_a)
    s.listen(5)

    while True:
        try:
            c, addr = s.accept()
            if os.fork()==0:
                s.close()
                server_client(c)
                return 0
            else:
                c.close()
        except:
            print("Exit")
            exit(1)

if __name__ == "__main__":
    main()