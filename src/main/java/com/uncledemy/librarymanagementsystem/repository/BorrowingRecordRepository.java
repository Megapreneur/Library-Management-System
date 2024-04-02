package com.uncledemy.librarymanagementsystem.repository;

import com.uncledemy.librarymanagementsystem.model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    BorrowingRecord findByBookIdAndPatronIdAndReturnDateIsNull(long bookId, long patronId);
}
