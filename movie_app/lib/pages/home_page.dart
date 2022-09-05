import 'package:flutter/material.dart';
import 'package:movie_app_api/pages/details_page.dart';
import 'package:movie_app_api/providers/movie_provider.dart';
import 'package:provider/provider.dart';

class HomePage extends StatefulWidget {
  static const String routeName = '/';
  const HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {



  late MovieProvider movieProvider;
  bool isFirst = true;

  @override
  void didChangeDependencies() {
    if (isFirst) {
      movieProvider = Provider.of<MovieProvider>(context);
      _getData();
    }
    isFirst = false;
    super.didChangeDependencies();
  }

  _getData() async {
    try {
      movieProvider.getAllMovie();
    } catch (error) {
      rethrow;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey[700],
      appBar: AppBar(title: const Text('Movie List'),),
      body: movieProvider.hasDataLoaded ? ListView.builder(
        itemCount: movieProvider.movieResponse!.data!.movies!.length,
        itemBuilder: (context, index){
          return Padding(
            padding: const EdgeInsets.all(8.0),
            child: InkWell(
              onTap: (){
                Navigator.pushNamed(context, DetailsPage.routeName, arguments: movieProvider.movieResponse!.data!.movies![index]);
              },
              child: Card(
                color: Colors.grey[500],
                elevation: 10,
                child: Padding(
                  padding: const EdgeInsets.all(0.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Image.network(
                        '${movieProvider.movieResponse!.data!.movies![index].largeCoverImage}',
                        height: 300,
                        width: 350,
                        fit: BoxFit.cover,
                      ),
                      Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text('${movieProvider.movieResponse!.data!.movies![index].titleEnglish}', style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),),


                            Text('Uploaded Date : ${movieProvider.movieResponse!.data!.movies![index].dateUploaded}'),
                            Text('Length : ${movieProvider.movieResponse!.data!.movies![index].runtime} min'),
                            Row(
                              children: [
                                Icon(Icons.star, color: Colors.orange,),
                                Text('${movieProvider.movieResponse!.data!.movies![index].rating}'),
                              ],
                            ),
                          ],
                        ),
                      )

                    ],
                  ),
                ),
              ),
            ),
          );
        },
      ) : Center(
        child: Text('Data loading'),
      )
    );
  }
}
