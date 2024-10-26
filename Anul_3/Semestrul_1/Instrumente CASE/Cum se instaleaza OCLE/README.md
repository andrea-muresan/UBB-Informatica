# Instalare și Configurare OCLE

Instrucțiuni pentru instalarea și configurarea OCLE (Object Constraint Language Environment) versiunea 2.0.4.

---

## Pași de instalare

### 1. Descărcați și dezarhivați OCLE
- Accesați [pagina de descărcare OCLE](https://lci.cs.ubbcluj.ro/ocle/download.php) și descărcați fișierul **ocle-2.0.4.zip**.
- După descărcare, dezarhivați fișierul în locația dorită.

### 2. Instalați JDK-ul
- Descărcați versiunea JDK compatibilă de la acest [link JDK](https://github.com/andrea-muresan/UBB-Informatica/blob/main/Anul_3/Semestrul_1/Instrumente%20CASE/Cum%20se%20instaleaza%20OCLE/jdk-6u24-windows-i586.rar).
- Rulați fișierul executabil pentru a instala JDK-ul.

### 3. Configurați scriptul de lansare pentru OCLE
- Deschideți fișierul `run_windows.bat` din directorul OCLE, într-un editor de text.
- Înlocuiți tot conținutul acestui fișier cu textul specificat din [**text_run_windows.txt**](https://github.com/andrea-muresan/UBB-Informatica/blob/main/Anul_3/Semestrul_1/Instrumente%20CASE/Cum%20se%20instaleaza%20OCLE/text_run_windows.txt).

### 4. Setați variabila JAVA_HOME
- Găsiți locația de instalare a JDK-ului (exemplu: `C:\Program Files (x86)\Java\jdk1.6.0_24`).
- În fișierul `run_windows.bat` inserați calea către JDK la linia `SET JAVA_HOME=...`, astfel:
  ```bat
  SET JAVA_HOME=C:\Program Files (x86)\Java\jdk1.6.0_24
  ```

  ### 5. Deschideți OCLE
- După configurarea fișierului, rulați run_windows.bat pentru a deschide și folosi OCLE.
