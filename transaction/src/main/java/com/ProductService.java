package com;

import com.Product;
import com.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final String THIRD_PARTY_API_URL = "https://s3.amazonaws.com/roxiler.com/product_transaction.json";

    public void initializeDatabase() {
        RestTemplate restTemplate = new RestTemplate();
        Product[] products = restTemplate.getForObject(THIRD_PARTY_API_URL, Product[].class);
        if (products != null) {
            productRepository.saveAll(Arrays.asList(products));
        }
    }

    public List<Product> findAll(int page, int perPage, String search) {
        List<Product> products = productRepository.findAll();
        if (search != null && !search.isEmpty()) {
            products = products.stream()
                    .filter(product -> product.getTitle().contains(search) ||
                            product.getDescription().contains(search) ||
                            String.valueOf(product.getPrice()).contains(search))
                    .collect(Collectors.toList());
        }
        int start = (page - 1) * perPage;
        int end = Math.min(start + perPage, products.size());
        return products.subList(start, end);
    }

    public Statistics getStatistics(String month) {
        List<Product> products = getProductsByMonth(month);
        double totalSaleAmount = products.stream()
                .filter(Product::isSold)
                .mapToDouble(Product::getPrice)
                .sum();
        long totalSoldItems = products.stream().filter(Product::isSold).count();
        long totalNotSoldItems = products.stream().filter(product -> !product.isSold()).count();
        Statistics stats = new Statistics();
        stats.setTotalSaleAmount(totalSaleAmount);
        stats.setTotalSoldItems(totalSoldItems);
        stats.setTotalNotSoldItems(totalNotSoldItems);
        return stats;
    }

    public List<BarChartData> getBarChartData(String month) {
        List<Product> products = getProductsByMonth(month);
        Map<String, Long> priceRangeCount = new HashMap<>();
        for (Product product : products) {
            String priceRange = getPriceRange(product.getPrice());
            priceRangeCount.put(priceRange, priceRangeCount.getOrDefault(priceRange, 0L) + 1);
        }
        return priceRangeCount.entrySet().stream()
                .map(entry -> {
                    BarChartData data = new BarChartData();
                    data.setPriceRange(entry.getKey());
                    data.setItemCount(entry.getValue());
                    return data;
                })
                .collect(Collectors.toList());
    }

    public List<PieChartData> getPieChartData(String month) {
        List<Product> products = getProductsByMonth(month);
        Map<String, Long> categoryCount = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
        return categoryCount.entrySet().stream()
                .map(entry -> {
                    PieChartData data = new PieChartData();
                    data.setCategory(entry.getKey());
                    data.setItemCount(entry.getValue());
                    return data;
                })
                .collect(Collectors.toList());
    }

    public CombinedData getCombinedData(String month) {
        CombinedData combinedData = new CombinedData();
        combinedData.setTransactions(findAll(1, 10, ""));
        combinedData.setStatistics(getStatistics(month));
        combinedData.setBarChartData(getBarChartData(month));
        combinedData.setPieChartData(getPieChartData(month));
        return combinedData;
    }

    private List<Product> getProductsByMonth(String month) {
        List<Product> products = productRepository.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        return products.stream()
                .filter(product -> sdf.format(product.getDateOfSale()).equalsIgnoreCase(month))
                .collect(Collectors.toList());
    }

    private String getPriceRange(double price) {
        if (price <= 100) return "0 - 100";
        else if (price <= 200) return "101 - 200";
        else if (price <= 300) return "201 - 300";
        else if (price <= 400) return "301 - 400";
        else if (price <= 500) return "401 - 500";
        else if (price <= 600) return "501 - 600";
        else if (price <= 700) return "601 - 700";
        else if (price <= 800) return "701 - 800";
        else if (price <= 900) return "801 - 900";
        else return "901 - above";
    }
}


