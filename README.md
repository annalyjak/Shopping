# Shopping List App

This is a simple Shopping list app where you can create a new shopping list and add items to buy.

## App architecture & technology

- App was made using MVP pattern.
- Almost all classes are written in Kotlin, only one is in Java (ui/history/ActivateListDialogFragment).
- Minimum SDK version: 21.
- The app should work in landscape and portrait.
- Apps supports any screen size & resolution.
- App has Room Database.
- Kotlin coroutines used to async tasks in Presenters.

## Views

Main view with list of all shopping lists:

![Main view](/screenshots/MainView.png)
![Main view - tablet version](/screenshots/MainViewTablet.png)


Add new shopping list:

![Add new shopping list 1](/screenshots/AddListView1.png)
![Add new shopping list 2](/screenshots/AddListView2.png)
![Add new shopping list - tablet version](/screenshots/AddListViewTablet.png)


Details view - management of shopping list and it's items:

![List of products](/screenshots/DetailsView.png)
![Details view - tablet view](/screenshots/DetailsViewTablet.png)
![Change list name](/screenshots/DetailsViewChangeName.png)
![Delete product](/screenshots/DetailsViewDeleteProduct.png)

Add product to list view:

![Add product](/screenshots/AddProductView.png)


History view (list of archived shopping lists:

![History view](/screenshots/HistoryView.png)
![History view - tablet](/screenshots/HistoryViewTablet.png)
![History view - empty](/screenshots/HistoryViewEmpty.png)
![History view - activate list](/screenshots/HistoryViewActivateList.png)


## Quick install

To install app: you can download app-release.apk added in app/release directory and directly install app on the phone.

Have fun!