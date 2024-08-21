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