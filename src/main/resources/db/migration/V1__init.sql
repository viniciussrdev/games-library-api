-- Criação da tabela User
CREATE TABLE tb_user (
    id_user BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    plan ENUM('NOOB', 'PRO', 'LEGEND') NOT NULL,
    active_loans INT NOT NULL
);

-- Criação da tabela Game
CREATE TABLE tb_game (
    id_game BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    genre ENUM(
        'ACTION', 'ADVENTURE', 'BATTLE_ROYALE', 'FIGHTING', 'FPS', 'HACK_N_SLASH', 'HORROR',
        'METROIDVANIA', 'MOBA', 'MMORPG', 'PLATFORMER', 'PUZZLE', 'RACING', 'ROGUELIKE',
        'RPG', 'RTS', 'SIMULATION', 'SOULSLIKE', 'SPORTS', 'SURVIVAL'
    ) NOT NULL,
    studio VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    available BOOLEAN NOT NULL
);

-- Criação da tabela Game Platform (Element Collection)
CREATE TABLE tb_game_platform (
    game_id BIGINT NOT NULL,
    platform ENUM('PLAYSTATION', 'XBOX', 'NINTENDO', 'MOBILE', 'PC', 'OTHER') NOT NULL,
    PRIMARY KEY (game_id, platform),
    FOREIGN KEY (game_id) REFERENCES tb_game(id_game) ON DELETE CASCADE
);

-- Criação da tabela Loan
CREATE TABLE tb_loan (
    id_loan BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    loan_date DATE NOT NULL,
    return_date DATE,
    status ENUM('ACTIVE', 'RETURNED', 'LATE') NOT NULL,
    FOREIGN KEY (game_id) REFERENCES tb_game(id_game),
    FOREIGN KEY (user_id) REFERENCES tb_user(id_user)
);
