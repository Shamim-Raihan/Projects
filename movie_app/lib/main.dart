import 'package:flutter/material.dart';
import 'package:movie_app_api/pages/details_page.dart';
import 'package:movie_app_api/pages/home_page.dart';
import 'package:movie_app_api/providers/movie_provider.dart';
import 'package:provider/provider.dart';

void main() {
  runApp(ChangeNotifierProvider(
    create: (_) => MovieProvider(),
    child: const MyApp(),
  ));
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is  root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Demo',
      theme: ThemeData(

        primarySwatch: Colors.blue,
      ),
      initialRoute: HomePage.routeName,
      routes: {
        HomePage.routeName : (_) => HomePage(),
        DetailsPage.routeName : (_) => DetailsPage(),
      },
    );
  }
}

