SET SERVEROUTPUT ON;
SET VERIFY OFF;


DROP TABLE AlertaRedeApoio CASCADE CONSTRAINTS;
DROP TABLE Alerta CASCADE CONSTRAINTS;
DROP TABLE Leitura CASCADE CONSTRAINTS;
DROP TABLE Sensor CASCADE CONSTRAINTS;
DROP TABLE LocalMonitorado CASCADE CONSTRAINTS;
DROP TABLE Usuario CASCADE CONSTRAINTS;
DROP TABLE RedeDeApoio CASCADE CONSTRAINTS;



CREATE TABLE Usuario (
    idUsuario NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    nome VARCHAR2(100),
    email VARCHAR2(100),
    senha VARCHAR2(100)
);

CREATE TABLE LocalMonitorado (
    idLocal NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    idUsuario NUMBER,
    nomeLocal VARCHAR2(100),
    cidade VARCHAR2(100),
    estado VARCHAR2(2),
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
);

CREATE TABLE Sensor (
    idSensor NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    nomeSensor VARCHAR2(100),
    idLocal NUMBER,
    FOREIGN KEY (idLocal) REFERENCES LocalMonitorado(idLocal)
);

CREATE TABLE Leitura (
    idLeitura NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    idSensor NUMBER,
    temperatura NUMBER,
    dataLeitura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idSensor) REFERENCES Sensor(idSensor)
);

CREATE TABLE Alerta (
    idAlerta NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    idLeitura NUMBER,
    tipo VARCHAR2(50),
    status VARCHAR2(20),
    dataAlerta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idLeitura) REFERENCES Leitura(idLeitura)
);

CREATE TABLE RedeDeApoio (
    idRede NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY PRIMARY KEY,
    nomeInstituicao VARCHAR2(100),
    contato VARCHAR2(100),
    cidade VARCHAR2(100),
    estado VARCHAR2(2)
);

-- Tabela associativa entre Alerta e RedeDeApoio
CREATE TABLE AlertaRedeApoio (
    idAlerta NUMBER,
    idRede NUMBER,
    PRIMARY KEY (idAlerta, idRede),
    FOREIGN KEY (idAlerta) REFERENCES Alerta(idAlerta),
    FOREIGN KEY (idRede) REFERENCES RedeDeApoio(idRede)
);



CREATE OR REPLACE FUNCTION media_temperatura
RETURN NUMBER IS
    v_media NUMBER;
BEGIN
    SELECT AVG(temperatura) INTO v_media FROM Leitura;
    RETURN v_media;
END;


CREATE OR REPLACE FUNCTION total_alertas_estado(p_estado VARCHAR2)
RETURN NUMBER IS
    v_total NUMBER;
BEGIN
    SELECT COUNT(*)
    INTO v_total
    FROM Alerta a
    JOIN Leitura l ON a.idLeitura = l.idLeitura
    JOIN Sensor s ON l.idSensor = s.idSensor
    JOIN LocalMonitorado lm ON s.idLocal = lm.idLocal
    WHERE lm.estado = p_estado;

    RETURN v_total;
END;



DECLARE
    CURSOR c_alertas IS
        SELECT lm.cidade, COUNT(a.idAlerta) AS total_alertas
        FROM Alerta a
        JOIN Leitura l ON a.idLeitura = l.idLeitura
        JOIN Sensor s ON l.idSensor = s.idSensor
        JOIN LocalMonitorado lm ON s.idLocal = lm.idLocal
        WHERE lm.estado = 'SP'
        GROUP BY lm.cidade;

    v_cidade VARCHAR2(100);
    v_total NUMBER;
BEGIN
    OPEN c_alertas;
    LOOP
        FETCH c_alertas INTO v_cidade, v_total;
        EXIT WHEN c_alertas%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Cidade: ' || v_cidade || ' - Total de Alertas: ' || v_total);
    END LOOP;
    CLOSE c_alertas;
END;





BEGIN
    INSERT INTO Usuario (nome, email, senha) VALUES ('Joana Mendes', 'joana@email.com', 'senha123');
    INSERT INTO Usuario (nome, email, senha) VALUES ('Carlos Silva', 'carlos@email.com', 'senha456');
    INSERT INTO Usuario (nome, email, senha) VALUES ('Ana Beatriz', 'ana@email.com', 'senha789');
    COMMIT;
END;



BEGIN
    INSERT INTO LocalMonitorado (idUsuario, nomeLocal, cidade, estado) VALUES (1, 'Parque Central', 'Santo Andr�', 'SP');
    INSERT INTO LocalMonitorado (idUsuario, nomeLocal, cidade, estado) VALUES (2, 'Pra�a da S�', 'S�o Paulo', 'SP');
    INSERT INTO LocalMonitorado (idUsuario, nomeLocal, cidade, estado) VALUES (3, 'Orla de Copacabana', 'Rio de Janeiro', 'RJ');
    COMMIT;
END;



BEGIN
    INSERT INTO Sensor (nomeSensor, idLocal) VALUES ('Sensor Calor 01', 1);
    INSERT INTO Sensor (nomeSensor, idLocal) VALUES ('Sensor Calor 02', 2);
    INSERT INTO Sensor (nomeSensor, idLocal) VALUES ('Sensor Calor 03', 3);
    COMMIT;
END;



BEGIN
    INSERT INTO Leitura (idSensor, temperatura) VALUES (1, 39.5);
    INSERT INTO Leitura (idSensor, temperatura) VALUES (2, 41.2);
    INSERT INTO Leitura (idSensor, temperatura) VALUES (3, 37.8);
    INSERT INTO Leitura (idSensor, temperatura) VALUES (1, 40.1);
    COMMIT;
END;



BEGIN
    INSERT INTO Alerta (idLeitura, tipo, status) VALUES (1, 'Onda de Calor', 'Ativo');
    INSERT INTO Alerta (idLeitura, tipo, status) VALUES (2, 'Temperatura Cr�tica', 'Ativo');
    INSERT INTO Alerta (idLeitura, tipo, status) VALUES (4, 'Alerta Moderado', 'Resolvido');
    COMMIT;
END;



BEGIN
    INSERT INTO RedeDeApoio (nomeInstituicao, contato, cidade, estado) VALUES ('Cruz Vermelha', 'contato@cruzvermelha.org', 'S�o Paulo', 'SP');
    INSERT INTO RedeDeApoio (nomeInstituicao, contato, cidade, estado) VALUES ('Defesa Civil RJ', 'contato@defesacivil.rj.gov.br', 'Rio de Janeiro', 'RJ');
    INSERT INTO RedeDeApoio (nomeInstituicao, contato, cidade, estado) VALUES ('ONG Alerta Verde', 'contato@alertaverde.org', 'Campinas', 'SP');
    COMMIT;
END;


-- Associa��o entre Alertas e Redes de Apoio
BEGIN
    INSERT INTO AlertaRedeApoio (idAlerta, idRede) VALUES (1, 1); -- Cruz Vermelha no alerta 1
    INSERT INTO AlertaRedeApoio (idAlerta, idRede) VALUES (2, 2); -- Defesa Civil no alerta 2
    INSERT INTO AlertaRedeApoio (idAlerta, idRede) VALUES (2, 3); -- ONG Alerta Verde tamb�m no alerta 2
    COMMIT;
END;


DECLARE
    v_idLocal NUMBER;
    v_idSensor NUMBER;
BEGIN
    INSERT INTO LocalMonitorado (idUsuario, nomeLocal, cidade, estado)
    VALUES (1, 'Zona Norte', 'S�o Paulo', 'SP')
    RETURNING idLocal INTO v_idLocal;

    INSERT INTO Sensor (nomeSensor, idLocal)
    VALUES ('Sensor Zona Norte', v_idLocal)
    RETURNING idSensor INTO v_idSensor;

    INSERT INTO Leitura (idSensor, temperatura)
    VALUES (v_idSensor, 42.3);

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Erro na transa��o: ' || SQLERRM);
END;


SELECT * FROM Usuario;
SELECT * FROM LocalMonitorado;
SELECT * FROM Sensor;
SELECT * FROM Leitura;
SELECT * FROM Alerta;
SELECT * FROM RedeDeApoio;
SELECT * FROM AlertaRedeApoio;
