# CFP-wiki-searcher
此 Android App 專案旨在改善 CFP wiki 使用者搜尋與瀏覽體驗。
## 📱 專案簡介

CFP Wiki Searcher 是一款針對 CFP wiki 資料整理與推薦的離線 Android 應用，透過直覺介面設計與簡化操作流程，讓使用者能夠更快速地找到符合需求的徵稿資訊。

---

## 🔍 功能特色

- 🔎 **條件篩選搜尋**
  - 關鍵字搜尋
  - 活動開始時間與徵稿截止時間區間篩選
  - 支援前後序排序
- 📋 **直覺化資訊展示**
  - 使用 `RecyclerView` 顯示文章列表
  - 清楚標示時間與主題資訊
- 🤖 **文章推薦系統**
  - 採用 `GloVe` 詞向量與 `NLTK` 相似度分析
  - 對文章進行關鍵詞分析與詞幹提取
  - 以相似度矩陣推薦未讀相關文章
- 📂 **本地資料儲存**
  - 使用 SQLite 資料庫，無需遠端連線
  - 範例資料共 23 筆，來源為 CFP wiki

---

## 🏗️ 技術架構

| 技術項目     | 說明                         |
|--------------|------------------------------|
| 語言         | Java                         |
| 開發工具     | Android Studio               |
| 資料儲存     | SQLite（本地端）             |
| UI 元件      | RecyclerView、DatePickerDialog |
| AI 模組      | NLTK + GloVe 詞向量（離線前處理） |

---

## 💡 系統設計理念

- 優化搜尋效率與結果相關性
- 精簡使用者操作步驟
- 提升資訊查找與瀏覽的流暢體驗

---

## 🛠️ 使用方式

1. 使用 Android Studio 開啟專案。
2. 將 SQLite 資料檔放入 `assets` 資料夾（含 CFP wiki 的 23 筆範例資料）。
3. 執行模擬器或部署至 Android 裝置。
4. 在首頁輸入關鍵字並可使用時間篩選與排序功能。
5. 點選文章後可檢視詳細資訊與推薦相關文章。

---

## 👥 團隊成員與分工

- **成員1**：負責 RecyclerView 架構設計與 AI 模組研究。
- **成員2**：負責 UI 設計、搜尋功能與資料處理整合。

---

## 📚 參考資源

- [DatePickerDialog 使用方法與取消按鈕設計](https://www.cnblogs.com/c2soft/articles/15658964.html)
- [SQLite Order By 排序](https://blog.csdn.net/whatday/article/details/104746456)
- [RecyclerView 基礎教學](https://thumbb13555.pixnet.net/blog/post/311803031)
- [如何根據日期排序 RecyclerView](https://www.volcengine.com/theme/6026322-R-7-1)
- [NLTK (Natural Language Toolkit)](https://www.nltk.org/)
- [GloVe: Global Vectors for Word Representation](https://nlp.stanford.edu/projects/glove/)

---

## 📌 備註

此專案為概念驗證（Proof of Concept），主要以本地資料與演算法為基礎，未串接後端伺服器。推薦功能之相似度矩陣建構於前處理階段完成，未於 App 內部即時運算。

---
