import 'dart:io';

import 'package:flutter/material.dart';
import 'package:movie_app_api/models/movie_response.dart';
import 'package:movie_app_api/providers/movie_provider.dart';
import 'package:provider/provider.dart';

class DetailsPage extends StatefulWidget {
  static const String routeName = '/details_page';
  const DetailsPage({Key? key}) : super(key: key);

  @override
  State<DetailsPage> createState() => _DetailsPageState();
}

class _DetailsPageState extends State<DetailsPage> {

  late MovieProvider movieProvider;
  late Movies? movies;

  @override
  void didChangeDependencies() {
    movieProvider = Provider.of<MovieProvider>(context);
    movies = ModalRoute.of(context)!.settings.arguments as Movies;
    super.didChangeDependencies();
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Movie Details'),),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Image.network(
                  '${movies!.mediumCoverImage}',
                  fit: BoxFit.cover,
                  height: 200,
                ),
                Padding(
                  padding: const EdgeInsets.only(left: 8),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.start,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text('${movies!.title}', style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 20),),
                      Text('${movies!.year}', style: const TextStyle(fontWeight: FontWeight.w400, fontSize: 20),),
                      SizedBox(height: 20,),
                      Row(
                        children: [
                          Icon(Icons.star, color: Colors.orange,),
                          Text('${movies!.rating}'),
                        ],
                      ),
                      SizedBox(height: 10,),
                      Text('Date : ${movies!.dateUploaded}', style: const TextStyle(fontWeight: FontWeight.w400, fontSize: 16),),
                      SizedBox(height: 10,),
                      Text('Length : ${movies!.runtime} min', style: const TextStyle(fontWeight: FontWeight.w400, fontSize: 16),),
                      SizedBox(height: 10,),
                      movies!.torrents!.isNotEmpty ? Text('Torrent : Yes(${movies!.torrents!.length})') : const Text('Torrent : No')
                    ],
                  ),
                ),

              ],
            ),

            SizedBox(height: 50,),
            Text('Movie description', style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 20),),
            SizedBox(height: 20,),
            Text('${movies!.descriptionFull}', style: const TextStyle(fontSize: 20),),

          ],
        ),
      ),
    );
  }
}
