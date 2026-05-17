# Final Project QA – Resonance LMS B2B

## 📌 Deskripsi
Proyek ini merupakan final project QA Bootcamp Batch 3 Dibimbing. Objek pengujian adalah aplikasi **Resonance LMS B2B** dengan fokus pada fitur kritikal seperti Employee dan Program Studi.  
Tujuan utama adalah memastikan kualitas aplikasi melalui kombinasi **manual testing**, **automation testing**, **regression testing**, dan **cross‑browser testing**.

---

## 🎯 Tujuan
- Mengidentifikasi requirement aplikasi dan ruang lingkup pengujian.
- Menyusun test plan dan strategi pengujian (manual + automation).
- Membuat dan mengeksekusi test case positif, negatif, dan edge case.
- Melakukan manual testing untuk menemukan defect awal.
- Membangun automation testing dengan Selenium (UI) dan REST Assured (API).
- Menjalankan regression suite untuk memastikan bug tidak muncul kembali.
- Melakukan cross‑browser testing untuk memastikan konsistensi aplikasi.
- Mendokumentasikan defect melalui bug report.
- Menyusun laporan QA yang komprehensif.

---

## 🛠 Tools & Framework
- **UI Automation**: Selenium WebDriver, TestNG, Gradle
- **API Automation**: REST Assured, GraphQL
- **Reporting**: Allure Report
- **Project Management**: Jira / Trello (defect tracking, test case management)
- **Evidence Manual Testing**: Screenshot, log, video (JamDev)

---

## 📑 Ruang Lingkup Pengujian
### Employee
- Add, Edit, Delete, Toggle Status, Resend Email, Transfer, Import/Export
- Search real‑time, Filter angkatan, Sorting, Pagination, Copy‑Paste Icon

### Program Studi
- Add, Edit, Delete, Search, Export Employee per Program Studi
- Sorting, Pagination, Copy‑Paste Icon

### API (GraphQL)
- Query: employees, employeeById, programStudis
- Mutation: create/update/deleteEmployee, activate/inactivate, resendEmail, import/export, updateEmployeesDivision, create/update/deleteProgram, exportAssignedEmployee

---

## 📊 Hasil Eksekusi
### Manual Testing
- **Total**: 86 TC
- **Pass**: 71 (82,6%)
- **Fail**: 15 (17,4%)
- **Defect**: 4 Critical, 8 High, 3 Medium

### Automation Testing
- **Total**: 52 TC
- **Pass**: 49 (94,2%)
- **Fail**: 1 (1,9%)
- **Broken**: 2 (3,9%)

### Regression Testing
- **Total**: 67 TC
- **Pass**: 56 (83,6%)
- **Fail**: 12 (17,9%)
- **Broken**: 2 (3,0%)

### Cross‑Browser Testing
- Chrome (baseline): stabil
- Edge: perbedaan minor rendering ikon & timing toast
- Firefox: perbedaan minor di interaksi/pagination
- **Kesimpulan**: kompatibel lintas browser, tidak ada error blocking

---

## 📌 Kesimpulan & Rekomendasi
- Sistem **belum layak release penuh** sampai defect Critical/High ditutup.
- Perlu perbaikan di validasi input (regex, required, max length) dan proses import/export.
- Assertion automation harus diperkuat agar konsisten dengan acceptance criteria manual.
- Test case & defect sebaiknya dikelola di **Jira/Trello** untuk transparansi tim.
- Evidence manual testing bisa ditingkatkan dengan video (JamDev) + log.
- Automation dapat diintegrasikan ke **CI/CD pipeline (GitHub Actions)** agar regression suite otomatis dijalankan setiap commit/pull request.

---

## 👤 Author
**Heru Chairuddin Mukti Utama**
