# ニュース管理システム（News Admin System）

Vue 3 + Spring Boot によるフルスタックニュース管理アプリ。ニュース記事（カバー画像・CRUD）・カテゴリ管理・個人アカウント設定（アバター/ニックネーム/パスワード変更・ログアウト）を実装。画像保存（記事カバー・アバター）にAlibaba Cloud OSS、認証にJWT、キャッシュにRedisを採用。

---

## ✨ 機能一覧

- 記事のCRUD
- カテゴリ管理
- ユーザー認証（JWT）
- 画像アップロード（OSS）
- ページネーション・フィルタリング
- Redisキャッシュ

---



## 🛠️ 技術スタック

**バックエンド**

- Spring Boot
- MyBatis-Plus
- PostgreSQL
- Redis（セッション管理/キャッシュ）
- JWT（認証）
- Alibaba Cloud OSS（画像アップロード）
- Validation

**フロントエンド**

- Vue 3
- Element Plus
- Axios / Fetch API

**その他**

- Maven

- Git

  

---

## 🏗️ システムアーキテクチャ

```mermaid
flowchart TD

A["Vue 3 SPA (news-admin)"]
B["Vite Dev Proxy (/api)"]
C["Spring Boot (NewsAdminApplication)"]
D["PostgreSQL (news_admin)"]
E["Redis (Token Store)"]
F["Alibaba Cloud OSS"]

A --> B
B --> C
C --> D
C --> E
C --> F

subgraph subGraph0 ["Client Layer (Frontend)"]
    A
end

subgraph subGraph1 ["API Gateway / Proxy"]
    B
end

subgraph subGraph2 ["Application Layer (Backend)"]
    C
end

subgraph subGraph3 ["Data & Storage Layer"]
    D
    E
    F
end
```

---

## 📖 ドキュメント

- [環境構築手順](./docs/setup.md)
- [APIドキュメント](./docs/api.md)

---



## 🖼️ スクリーンショット

### 1. ログイン / 登録（JWT認証）

![新規登録画面](./screenshots/login-register/register02.png)

*新規登録画面：ユーザー名、パスワードを登録*

![ログイン画面](./screenshots/login-register/login01.png)

![ログイン画面2](./screenshots/login-register/login02.png)

*ログイン画面：ユーザー名とパスワードを入力*

> 認証成功後、JWT トークンは localStorage に保存され、以降の API リクエストの Authorization ヘッダーに付与されます。

---

### 2. 記事管理（ニュース）

#### 記事作成

![記事作成画面](./screenshots/article/072843.png)

![記事作成画面](./screenshots/article/073047.png)

![記事作成画面](./screenshots/article/074824.png)

![記事作成画面](./screenshots/article/080232.png)

![記事作成画面](./screenshots/article/080241.png)

*リッチテキストエディタで記事を作成*

#### 記事編集

![記事編集画面](./screenshots/article/135730.png)

![記事作成画面](./screenshots/article/140004.png)

*既存記事の編集*

#### 記事削除（確認モーダル）

![記事削除確認](./screenshots/article/072808.png)

![記事作成画面](./screenshots/article/072817.png)

*削除前に確認ダイアログを表示*

#### 記事一覧（ページネーション・検索）

![一覧](./screenshots/article/082138.png)

*記事一覧：ページネーション、タイトル検索、カテゴリ絞り込みが可能*



---

### 3.カテゴリ管理

#### *カテゴリの一覧表示*

![カテゴリ一覧](./screenshots/category/141336.png)

*全カテゴリの一覧。カテゴリ名・別名・作成日時などを表示。*

#### カテゴリ削除

![カテゴリ一覧](./screenshots/category/category_delete01.png)

![カテゴリ一覧](./screenshots/category/category_delete.png)

*削除前に確認ダイアログを表示*

#### カテゴリ編集

![カテゴリ一覧](./screenshots/category/category_update01.png)

![カテゴリ一覧](./screenshots/category/category_update02.png)

*カテゴリ名・別名の編集*

#### カテゴリを追加

![カテゴリ一覧](./screenshots/category/category_save01.png)

![カテゴリ一覧](./screenshots/category/category_save02.png)

*新しいカテゴリを作成するダイアログ。*

---

### 4. 個人設定 / アバターアップロード



#### アバターアップロード（Using OSS）

![カテゴリ一覧](./screenshots/user/145958.png)

![カテゴリ一覧](./screenshots/user/150007.png)

*画像を選択してアップロード*

![アップロード成功後](./screenshots/user/150017.png)

*アップロード完了後、アバターが更新される*

#### ニックネームとメールアドレス変更

![パスワード変更画面](./screenshots/user/082938.png)

![パスワード変更画面](./screenshots/user/082946.png)

*新しいニックネームとメールアドレスを入力して更新*

#### パスワード変更

![パスワード変更画面](./screenshots/user/083202.png)

![パスワード変更画面](./screenshots/user/083212.png)

*パスワード変更フォーム*

> アバター画像は Alibaba Cloud OSS に保存され、返却された URL をユーザープロフィールに保存します。

---

### 5. 技術スタックの補足（OSS）

#### Alibaba OSS 管理画面（バケット設定）

![OSSバケット](./screenshots/oss/111255.png)

![OSSバケット](./screenshots/oss/151639.png)

*OSS バケット内にアップロードされた画像ファイル*



## 👤 作者

Donn67
