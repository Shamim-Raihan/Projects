import 'package:ecom_admin_batch5/providers/order_provider.dart';
import 'package:ecom_admin_batch5/utils/constants.dart';
import 'package:ecom_admin_batch5/utils/helper_functions.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class OrderListPage extends StatelessWidget {
  static const String routeName = '/order_list';

  const OrderListPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final orderFilter = ModalRoute.of(context)!.settings.arguments as OrderFilter;
    final orderList = Provider.of<OrderProvider>(context, listen: false).getFilteredOrderList(orderFilter);
    return Scaffold(
      appBar: AppBar(title: const Text('Order List'),),
      body: ListView.builder(
        itemCount: orderList.length,
        itemBuilder: (context, index) {
          final order = orderList[index];
          return ListTile(
            title: Text(getFormattedDateTime(order.orderDate.timestamp.toDate(), 'dd/MM/yyyy hh:mm:ss a')),
            trailing: Text('$currencySymbol${order.grandTotal}'),
          );
        },
      )
    );
  }
}
