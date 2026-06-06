# API ドキュメント

## UserController（`/user`）

認証、ユーザー情報取得、アカウント更新を処理します。

| メソッド | パス                 | リクエストボディ / パラメータ | レスポンスデータ | 説明                       |
| -------- | -------------------- | ----------------------------- | ---------------- | -------------------------- |
| `POST`   | `/user/register`     | `UserRegisterDTO`             | `null`           | 新規ユーザー登録           |
| `POST`   | `/user/login`        | `UserLoginDTO`                | `String` (JWT)   | ログイン認証、トークン返却 |
| `GET`    | `/user/userinfo`     | なし                          | `UserVO`         | 現在のユーザー情報を取得   |
| `PUT`    | `/user/update`       | `UserUpdateDTO`               | `null`           | ニックネーム・メールを更新 |
| `PATCH`  | `/user/updateAvatar` | `avatarUrl`（クエリ）         | `null`           | アバターURLを更新          |
| `PATCH`  | `/user/updatePwd`    | `UserPwdUpdateDTO`            | `null`           | パスワードを更新           |

> **補足**：`updatePwd` では、サービス層呼び出し前に `newPwd` と `rePwd` の一致をチェックします。

---

## CategoryController（`/category`）

記事カテゴリーのCRUD操作を提供します。

| メソッド | パス             | リクエストボディ / パラメータ | レスポンスデータ   | 説明                     |
| -------- | ---------------- | ----------------------------- | ------------------ | ------------------------ |
| `POST`   | `/category`      | `CategorySaveDTO`             | `null`             | カテゴリーを作成         |
| `GET`    | `/category`      | なし                          | `List<CategoryVO>` | 全カテゴリーを一覧取得   |
| `GET`    | `/category/{id}` | `id`（パス）                  | `CategoryVO`       | ID指定でカテゴリーを取得 |
| `PUT`    | `/category`      | `CategoryUpdateDTO`           | `null`             | カテゴリーを更新         |
| `DELETE` | `/category/{id}` | `id`（パス）                  | `null`             | カテゴリーを削除         |

---

## ArticleController（`/article`）

記事の作成・閲覧・更新・削除、ページネーション・フィルタリングを処理します。

| メソッド | パス            | リクエストボディ / パラメータ | レスポンスデータ    | 説明                         |
| -------- | --------------- | ----------------------------- | ------------------- | ---------------------------- |
| `POST`   | `/article`      | `ArticleSaveDTO`              | `null`              | 新規記事を作成               |
| `GET`    | `/article`      | `ArticleQueryDTO`             | `PageVO<ArticleVO>` | 記事一覧（ページ＋絞り込み） |
| `PUT`    | `/article`      | `ArticleUpdateDTO`            | `null`              | 既存記事を更新               |
| `DELETE` | `/article/{id}` | `id`（パス）                  | `null`              | 指定記事を削除               |

---

## ファイルアップロード（`/upload`）

画像ファイルをAliyun OSSにアップロードし、公開URLを返します。

| メソッド | パス      | リクエストパラメータ      | レスポンスデータ            | 説明               |
| -------- | --------- | ------------------------- | --------------------------- | ------------------ |
| `POST`   | `/upload` | `file`（`MultipartFile`） | `Result<String>`（画像URL） | 画像をアップロード |

> **補足**：アップロードされたファイルはOSSに保存されます。