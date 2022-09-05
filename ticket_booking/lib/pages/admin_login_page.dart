import 'package:flutter/material.dart';
import 'package:ticket_booking/authentication.dart';
import 'package:ticket_booking/pages/admin_dashboard_page.dart';

class AdminLogInPage extends StatefulWidget {
  static const String roureName = '/admin_log_in';

  const AdminLogInPage({Key? key}) : super(key: key);

  @override
  State<AdminLogInPage> createState() => _AdminLogInPageState();
}

class _AdminLogInPageState extends State<AdminLogInPage> {
  final emailController = TextEditingController();
  final passwordController = TextEditingController();

  @override
  void dispose() {
    emailController.dispose();
    passwordController.dispose();
    super.dispose();
  }
  final from_key=GlobalKey<FormState>();

  bool isObscure = true;
  bool isUserExist = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Admin log in'),
      ),
      body: SingleChildScrollView(
        child: Center(
          child: Padding(
            padding: const EdgeInsets.only(left: 24, right: 24,top: 24),
            child: Form(
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Text('Admin Login', style: TextStyle(fontSize: 25, fontWeight: FontWeight.bold, color: Colors.black,),),
                  Image.asset('images/login_page_image.png', height: 300, width: 300,),
                  SizedBox(
                    height: 40,
                  ),
                  TextField(
                    controller: emailController,
                    keyboardType: TextInputType.emailAddress,
                    decoration: InputDecoration(
                      labelText: 'Enter your Email',
                      prefixIcon: Icon(Icons.email),
                      fillColor: Colors.grey[200],
                      filled: true,
                      border: OutlineInputBorder(
                        borderSide: BorderSide.none,
                        borderRadius: BorderRadius.circular(15.0),
                      )

                    ),
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  TextField(
                    controller: passwordController,
                    keyboardType: TextInputType.visiblePassword,
                    obscureText: isObscure,
                    decoration: InputDecoration(
                      labelText: 'Enter your Password',
                      prefixIcon: Icon(Icons.lock),
                      suffixIcon: IconButton(
                        icon:
                        Icon(isObscure ? Icons.visibility_off : Icons.visibility),
                        onPressed: () {
                          setState(() {
                            isObscure = !isObscure;
                          });
                        },
                      ),
                      fillColor: Colors.grey[200],
                      filled: true,
                      border: OutlineInputBorder(
                        borderSide: BorderSide.none,
                        borderRadius: BorderRadius.circular(20.0),
                      ),
                    ),
                  ),
                  SizedBox(height: 80,),

                  ElevatedButton(onPressed: (){
                    if(emailController.text.isEmpty || emailController.text == null){
                      final snackBar = SnackBar(
                          content: const Text('Email required!'),
                          action: SnackBarAction(
                            label: 'Ok',
                            onPressed: () {
                              // Some code to undo the change.
                            },
                          ),
                      );
                      ScaffoldMessenger.of(context).showSnackBar(snackBar);
                      return;
                    }

                    if(passwordController.text.isEmpty || passwordController == null){
                      final snackBar = SnackBar(
                        content: const Text('Password required!'),
                        action: SnackBarAction(
                          label: 'Ok',
                          onPressed: () {
                            // Some code to undo the change.
                          },
                        ),
                      );
                      ScaffoldMessenger.of(context).showSnackBar(snackBar);
                      return;
                    }
                    if(emailController.text == 'shamim@gmail.com' && passwordController.text == '123456'){
                      setSession(true).then((value) {
                        Navigator.pushReplacementNamed(context, AdminDashboardPage.roureName);
                      });

                      final snackBar = SnackBar(
                        content: const Text('Log in successfull!'),
                        action: SnackBarAction(
                          label: 'Ok',
                          onPressed: () {
                            // Some code to undo the change.
                          },
                        ),
                      );
                      ScaffoldMessenger.of(context).showSnackBar(snackBar);
                      return;

                    }
                    else {
                      final snackBar = SnackBar(
                        content: const Text('Invalid Email or Password!'),
                        action: SnackBarAction(
                          label: 'Ok',
                          onPressed: () {
                            // Some code to undo the change.
                          },
                        ),
                      );
                      ScaffoldMessenger.of(context).showSnackBar(snackBar);
                      return;
                    }
                  }, child: Text("Log In"),
                      style: ButtonStyle(
                        shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                            RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(30.0),

                            )
                        ),
                        padding: MaterialStateProperty.resolveWith<EdgeInsetsGeometry>(
                                (Set<MaterialState> states){
                              return EdgeInsets.only(left: 135, right: 135, top: 15, bottom: 15);
                            }
                        ),
                        backgroundColor: MaterialStateProperty.all<Color>(Colors.green),
                      )
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }

  bool _login() {
    if(from_key.currentState!.validate()){
      if(emailController.text == 'shamim@gmail.com' && passwordController.text == '123456'){
        isUserExist = !isUserExist;
      }
      else {
        isUserExist = false;
      }
    }
    return isUserExist;
  }

  void adminLogIn() {

  }
}

















