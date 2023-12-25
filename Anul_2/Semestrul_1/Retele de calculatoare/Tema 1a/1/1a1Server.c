#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <pthread.h>

void * socketThread(void *arg) {
        uint16_t n, numbers[100], sum=0;

        int c = *((int *)arg);
        recv(c, &n, sizeof(n), MSG_WAITALL);
        n = ntohs(n);
        recv(c, numbers, n * sizeof(uint16_t), MSG_WAITALL);
        for (int i = 0; i < n; i++) {
                numbers[i] = ntohs(numbers[i]);
                sum += numbers[i];
        }

        sum = htons(sum);
        send(c, &sum, sizeof(sum), 0);
        close(c);
        pthread_exit(NULL);
}

	int main() {
        int s;
        struct sockaddr_in server, client;
        int c, l;

        s = socket(AF_INET, SOCK_STREAM, 0);
        if (s < 0) {
                printf("Socket creation failed - server");
                exit(1);
        }
        memset(&server, 0, sizeof(server));
        server.sin_port = htons(1235);
        server.sin_family = AF_INET;
        server.sin_addr.s_addr = INADDR_ANY;

        if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
                printf("Bind error");
                exit(1);
        }

        listen(s, 5);

        l = sizeof(client);
        memset(&client, 0, sizeof(client));

        pthread_t thr[40];
        int i = 0;

        while(1) {
                c = accept(s, (struct sockaddr *) &client, &l);
                printf("Client connected\n");

                if(pthread_create(&thr[i++], NULL, socketThread, &c) != 0) {
                        printf("Failed to create thread\n");
                }

                if (i >= 30) {
                        i = 0;
                        while (i < 30) {
                                pthread_join(thr[i++], NULL);
                        }
                        i = 0;
                }
        }
}