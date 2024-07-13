package com;

import com.Product;
import com.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/initialize-database")
    public ResponseEntity<String> initializeDatabase() {
        try {
            productService.initializeDatabase();
            return ResponseEntity.ok("Database initialized successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error initializing database: " + e.getMessage());
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Product>> listTransactions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage,
            @RequestParam(required = false) String search
    ) {
        List<Product> products = productService.findAll(page, perPage, search);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Statistics> getStatistics(@RequestParam String month) {
        Statistics stats = productService.getStatistics(month);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/bar-chart")
    public ResponseEntity<List<BarChartData>> getBarChartData(@RequestParam String month) {
        List<BarChartData> data = productService.getBarChartData(month);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/pie-chart")
    public ResponseEntity<List<PieChartData>> getPieChartData(@RequestParam String month) {
        List<PieChartData> data = productService.getPieChartData(month);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/combined-data")
    public ResponseEntity<CombinedData> getCombinedData(@RequestParam String month) {
        CombinedData combinedData = productService.getCombinedData(month);
        return ResponseEntity.ok(combinedData);
    }
}
