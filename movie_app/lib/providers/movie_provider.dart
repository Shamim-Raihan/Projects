import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart';

import '../models/movie_response.dart';

class MovieProvider extends ChangeNotifier {

  MovieResponse? movieResponse;

  bool get hasDataLoaded => movieResponse != null;


  void getAllMovie() async {
    final uri = Uri.parse('https://yts.mx/api/v2/list_movies.json');
    try {
      final response = await get(uri);
      final map =jsonDecode(response.body);
      if(response.statusCode == 200){
        movieResponse = MovieResponse.fromJson(map);
        print(movieResponse!.data!.movies!.length);
        notifyListeners();
      }
    }catch(error){
      rethrow;
    }
  }


}