-- 既存のデータベースを削除
DROP DATABASE IF EXISTS news_admin;

-- データベース作成 (owner: yourself / 名前: news_admin)
CREATE DATABASE news_admin
    OWNER = yourself
    ENCODING = 'UTF8'
    LC_COLLATE = 'ja_JP.UTF-8'
    LC_CTYPE = 'ja_JP.UTF-8'
    TEMPLATE = template0;


-- 接続
\c news_admin;

-- ユーザーテーブル
CREATE TABLE "user" (
                        id           SERIAL PRIMARY KEY,
                        username     VARCHAR(20)  NOT NULL UNIQUE,
                        password     VARCHAR(32),
                        nickname     VARCHAR(10)  DEFAULT '',
                        email        VARCHAR(128) DEFAULT '',
                        user_pic     VARCHAR(128) DEFAULT '',
                        create_time  TIMESTAMP    NOT NULL,
                        update_time  TIMESTAMP    NOT NULL
);
COMMENT ON TABLE  "user" IS 'ユーザーテーブル';
COMMENT ON COLUMN "user".id          IS 'ID';
COMMENT ON COLUMN "user".username    IS 'ユーザー名';
COMMENT ON COLUMN "user".password    IS 'パスワード';
COMMENT ON COLUMN "user".nickname    IS 'ニックネーム';
COMMENT ON COLUMN "user".email       IS 'メールアドレス';
COMMENT ON COLUMN "user".user_pic    IS 'アバター';
COMMENT ON COLUMN "user".create_time IS '作成日時';
COMMENT ON COLUMN "user".update_time IS '更新日時';

-- カテゴリテーブル
CREATE TABLE category (
                          id            SERIAL PRIMARY KEY,
                          category_name VARCHAR(32) NOT NULL,
                          category_alias VARCHAR(32) NOT NULL,
                          create_user   INTEGER NOT NULL,
                          create_time   TIMESTAMP NOT NULL,
                          update_time   TIMESTAMP NOT NULL,
                          CONSTRAINT fk_category_user FOREIGN KEY (create_user) REFERENCES "user"(id)
);
COMMENT ON TABLE category IS 'カテゴリテーブル';
COMMENT ON COLUMN category.id            IS 'ID';
COMMENT ON COLUMN category.category_name IS 'カテゴリ名';
COMMENT ON COLUMN category.category_alias IS 'カテゴリ別名';
COMMENT ON COLUMN category.create_user   IS '作成者ID';
COMMENT ON COLUMN category.create_time   IS '作成日時';
COMMENT ON COLUMN category.update_time   IS '更新日時';

-- 記事テーブル
CREATE TABLE article (
                         id          SERIAL PRIMARY KEY,
                         title       VARCHAR(30)   NOT NULL,
                         content     VARCHAR(10000) NOT NULL,
                         cover_img   VARCHAR(128)  NOT NULL,
                         state       VARCHAR(3)    DEFAULT '下書き',
                         category_id INTEGER,
                         create_user INTEGER       NOT NULL,
                         create_time TIMESTAMP     NOT NULL,
                         update_time TIMESTAMP     NOT NULL,
                         CONSTRAINT fk_article_category FOREIGN KEY (category_id) REFERENCES category(id),
                         CONSTRAINT fk_article_user      FOREIGN KEY (create_user) REFERENCES "user"(id)
);
COMMENT ON TABLE article IS '記事テーブル';
COMMENT ON COLUMN article.id          IS 'ID';
COMMENT ON COLUMN article.title       IS '記事タイトル';
COMMENT ON COLUMN article.content     IS '記事内容';
COMMENT ON COLUMN article.cover_img   IS 'カバー画像';
COMMENT ON COLUMN article.state       IS '記事状態（「公開」または「下書き」のみ）';
COMMENT ON COLUMN article.category_id IS 'カテゴリID';
COMMENT ON COLUMN article.create_user IS '作成者ID';
COMMENT ON COLUMN article.create_time IS '作成日時';
COMMENT ON COLUMN article.update_time IS '更新日時';