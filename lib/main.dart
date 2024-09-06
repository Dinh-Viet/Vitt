import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter CRUD App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: ProductListScreen(),
    );
  }
}

class ProductListScreen extends StatefulWidget {
  @override
  _ProductListScreenState createState() => _ProductListScreenState();
}

class _ProductListScreenState extends State<ProductListScreen> {
  List<dynamic> products = [];

  @override
  void initState() {
    super.initState();
    fetchProducts();
  }

  Future<void> fetchProducts() async {
    final response = await http.get(Uri.parse('https://t2210m-flutter.onrender.com/products'));
    if (response.statusCode == 200) {
      setState(() {
        products = jsonDecode(response.body);
      });
    } else {
      throw Exception('Failed to load products');
    }
  }

  Future<void> createProduct(String name, String description, int price) async {
    final response = await http.post(
      Uri.parse('https://t2210m-flutter.onrender.com/products'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, dynamic>{
        'name': name,
        'description': description,
        'price': price,
      }),
    );

    if (response.statusCode == 201) {
      fetchProducts();
    } else {
      throw Exception('Failed to create product');
    }
  }

  Future<void> updateProduct(String id, String name, String description, int price) async {
    final response = await http.put(
      Uri.parse('https://t2210m-flutter.onrender.com/products/$id'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(<String, dynamic>{
        'name': name,
        'description': description,
        'price': price,
      }),
    );

    if (response.statusCode == 200) {
      fetchProducts();
    } else {
      throw Exception('Failed to update product');
    }
  }

  Future<void> deleteProduct(String id) async {
    final response = await http.delete(
      Uri.parse('https://t2210m-flutter.onrender.com/products/$id'),
    );

    if (response.statusCode == 200) {
      fetchProducts();
    } else {
      throw Exception('Failed to delete product');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Product List'),
      ),
      body: ListView.builder(
        itemCount: products.length,
        itemBuilder: (context, index) {
          final product = products[index];
          return ListTile(
            title: Text(product['name']),
            subtitle: Text('Price: \$${product['price']}'),
            trailing: Row(
              mainAxisSize: MainAxisSize.min,
              children: [
                IconButton(
                  icon: Icon(Icons.edit),
                  onPressed: () => _showUpdateProductDialog(product),
                ),
                IconButton(
                  icon: Icon(Icons.delete),
                  onPressed: () => deleteProduct(product['_id']),
                ),
              ],
            ),
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _showCreateProductDialog,
        child: Icon(Icons.add),
      ),
    );
  }

  void _showCreateProductDialog() {
    String name = '';
    String description = '';
    int price = 0;

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Create Product'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              TextField(
                onChanged: (value) => name = value,
                decoration: InputDecoration(labelText: 'Name'),
              ),
              TextField(
                onChanged: (value) => description = value,
                decoration: InputDecoration(labelText: 'Description'),
              ),
              TextField(
                onChanged: (value) => price = int.tryParse(value) ?? 0,
                decoration: InputDecoration(labelText: 'Price'),
                keyboardType: TextInputType.number,
              ),
            ],
          ),
          actions: <Widget>[
            TextButton(
              child: Text('Cancel'),
              onPressed: () => Navigator.of(context).pop(),
            ),
            TextButton(
              child: Text('Create'),
              onPressed: () {
                createProduct(name, description, price);
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  void _showUpdateProductDialog(Map<String, dynamic> product) {
    String name = product['name'];
    String description = product['description'];
    int price = product['price'];

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Update Product'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              TextField(
                onChanged: (value) => name = value,
                decoration: InputDecoration(labelText: 'Name'),
                controller: TextEditingController(text: name),
              ),
              TextField(
                onChanged: (value) => description = value,
                decoration: InputDecoration(labelText: 'Description'),
                controller: TextEditingController(text: description),
              ),
              TextField(
                onChanged: (value) => price = int.tryParse(value) ?? 0,
                decoration: InputDecoration(labelText: 'Price'),
                keyboardType: TextInputType.number,
                controller: TextEditingController(text: price.toString()),
              ),
            ],
          ),
          actions: <Widget>[
            TextButton(
              child: Text('Cancel'),
              onPressed: () => Navigator.of(context).pop(),
            ),
            TextButton(
              child: Text('Update'),
              onPressed: () {
                updateProduct(product['_id'], name, description, price);
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
}
