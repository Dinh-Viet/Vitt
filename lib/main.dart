<<<<<<< HEAD
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
=======
import 'dart:html' as html;
import 'package:flutter/foundation.dart' show kIsWeb;
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:youtube_player_flutter/youtube_player_flutter.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'YouTube Video Search',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: YouTubeScreen(),
    );
  }
}

class YouTubeScreen extends StatefulWidget {
  @override
  _YouTubeScreenState createState() => _YouTubeScreenState();
}

class _YouTubeScreenState extends State<YouTubeScreen> {
  final String apiKey = 'AIzaSyCgrYQcYX1ynQhl20l2zh0z75KG8BivMLQ'; // Thay thế bằng API key thực tế của bạn
  final TextEditingController _controller = TextEditingController();
  List videos = [];
  YoutubePlayerController? _youtubePlayerController;
  bool _isPlayerReady = false;
  String? _currentVideoId;

  @override
  void initState() {
    super.initState();
  }

  fetchVideos(String query) async {
    var url = Uri.parse('https://www.googleapis.com/youtube/v3/search?part=snippet&q=$query&type=video&key=$apiKey&maxResults=5');
    var response = await http.get(url);

    if (response.statusCode == 200) {
      var data = json.decode(response.body);
      setState(() {
        videos = data['items'];
      });
    } else {
      // Improved error handling
      setState(() {
        videos = [];
      });
      print('Failed to fetch videos');
    }
  }

  void playVideo(String videoId) {
    if (kIsWeb) {
      // Sử dụng IFrameElement để nhúng video YouTube trên nền tảng web
      html.window.open('https://www.youtube.com/watch?v=$videoId', '_blank');
    } else {
      if (_youtubePlayerController == null) {
        _youtubePlayerController = YoutubePlayerController(
          initialVideoId: videoId,
          flags: YoutubePlayerFlags(
            autoPlay: true,
            mute: false,
          ),
        )..addListener(listener);

        setState(() {
          _currentVideoId = videoId;
        });
      } else {
        if (_isPlayerReady) {
          _youtubePlayerController!.load(videoId);
          setState(() {
            _currentVideoId = videoId;
          });
        }
      }
    }
  }

  void listener() {
    if (_youtubePlayerController != null && _youtubePlayerController!.value.isReady && !_isPlayerReady) {
      _isPlayerReady = true;
    }
  }

  @override
  void dispose() {
    _youtubePlayerController?.dispose();
    _controller.dispose(); // Dispose the controller when no longer needed
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('YouTube Video Search'),
      ),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: TextField(
              controller: _controller,
              decoration: InputDecoration(
                labelText: 'Enter search term',
                suffixIcon: IconButton(
                  onPressed: () {
                    fetchVideos(_controller.text);
                  },
                  icon: Icon(Icons.search),
                ),
              ),
              onSubmitted: (value) {
                fetchVideos(value);
              },
            ),
          ),
          Expanded(
            child: ListView.builder(
              itemCount: videos.length,
              itemBuilder: (context, index) {
                var video = videos[index];
                return ListTile(
                  title: Text(video['snippet']['title']),
                  subtitle: Text(video['snippet']['description']),
                  leading: kIsWeb
                      ? Icon(Icons.video_library, size: 50) // Placeholder on web
                      : Image.network('https://i3.ytimg.com/vi/${video['id']['videoId']}/sddefault.jpg'), // Load image on mobile
                  onTap: () {
                    playVideo(video['id']['videoId']);
                  },
                );
              },
            ),
          ),
          if (_youtubePlayerController != null && !kIsWeb)
            Expanded(
              child: YoutubePlayer(
                controller: _youtubePlayerController!,
                showVideoProgressIndicator: true,
                onReady: () {
                  _isPlayerReady = true;
                },
              ),
            )
          else
            Container(
              padding: EdgeInsets.all(20),
              child: Text('Select a video to play'),
            ),
        ],
      ),
    );
  }
}
>>>>>>> b2333944e30464e7bc5fe0e01c5237e689ca6757
