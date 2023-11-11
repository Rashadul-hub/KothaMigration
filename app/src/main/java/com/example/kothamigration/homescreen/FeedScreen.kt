package com.example.kothamigration.homescreen


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.compose.rememberNavController
import com.example.kothamigration.R
import com.example.kothamigration.composablefunctions.SignInTitle
import com.example.kothamigration.composablefunctions.TitleText
import com.example.kothamigration.model.BottomNavGraph
import com.example.kothamigration.model.BottomNavbar
import com.example.kothamigration.model.Dimensions
import com.example.kothamigration.model.Post
import com.example.kothamigration.model.Stories
import com.example.kothamigration.model.User
import com.example.kothamigration.model.WindowSize
import com.example.kothamigration.model.compactDimensions
import com.example.kothamigration.model.largeDimensions
import com.example.kothamigration.model.mediumDimensions
import com.example.kothamigration.model.rememberWindowSizeClass
import com.example.kothamigration.model.smallDimensions
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen() {
    val navController = rememberNavController()
    val windowSize = rememberWindowSizeClass()

    val dimensions = when (windowSize.width) {
        is WindowSize.Small -> smallDimensions
        is WindowSize.Compact -> compactDimensions
        is WindowSize.Medium -> mediumDimensions
        is WindowSize.Large -> largeDimensions
    }
    // Determine whether the device is in landscape orientation
    val isLandscape = windowSize.width is WindowSize.Large

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
//                shadowElevation = dimensions.large

            ) {
                CenterAlignedTopAppBar(navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            Modifier
                                .size(32.dp)
                                .padding(1.dp)
                        )

                    }
                },title = {
                    SignInTitle(text = stringResource(id = R.string.bangladeshi_hero))
                })

                AppToolBar()

            }
        },
    ) { values ->
        // If in landscape mode, wrap the content in a scrollable LazyColumn
        if (isLandscape) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .padding(values)
                            .background(color = MaterialTheme.colorScheme.background) //White BackGround Landscape Mode
                    ) {
                        // Body Section
                        FeedScreenContents(dimensions)
                    }
                }
            }
        } else {
            // In portrait mode, we use this existing layout
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
                    .background(color = MaterialTheme.colorScheme.background) // White BackGround Color Portrait Mode
            ) {

                // Body Section
                FeedScreenContents(dimensions)
            }
        }

        //Bottom Navigation Bar


    }

}

@Composable
fun BottomNavigationBar() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavbar(navController) },

        ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BottomNavGraph(navHostController = navController)
        }
    }
}


@Composable
fun FeedScreenContents(dimensions: Dimensions) {

    Column(modifier = Modifier.fillMaxSize()) {
        // AppToolBar()

        StoriesSection(storyList = getStories())

        Divider(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .height(1.dp)
        )

        PostSection(Modifier.fillMaxWidth(), getPostData())

    }

}


/**  Feeds / Posts Section **/
@Composable
fun PostSection(modifier: Modifier, postList: List<Post>) {
    LazyColumn {
        items(postList) { post ->
            PostItem(post = post)

        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostItem(modifier: Modifier = Modifier, post: Post) {

    val pagerState = rememberPagerState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {

            /** Showing User's  Name & picture **/

            Row(
                modifier = Modifier
                    .align(CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = post.profile),
                    contentDescription = "profile",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = post.userName,
                    fontSize = 12.sp,
                    maxLines = 1,
                    modifier = Modifier.width(100.dp),
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
            }


            Icon(
                painter = painterResource(id = R.drawable.more),
                contentDescription = "more",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(22.dp)
                    .align(CenterEnd)

            )
        }


        /** Post Carousel - For Showing User's Post **/
        PostCarousel(post.postImageList, pagerState)

        /** Here Post like comment Send Icon
        Box(
        modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        ) {
        Row(modifier = Modifier.align(CenterStart)) {
        Icon(
        painter = painterResource(id = R.drawable.shop),
        contentDescription = "like",
        modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
        painter = painterResource(id = R.drawable.comment),
        contentDescription = "comment",
        modifier = Modifier.size(24.dp)


        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
        painter = painterResource(id = R.drawable.send),
        contentDescription = "send",
        modifier = Modifier.size(24.dp)


        )


        }


        /** Page Indicator Count **/
        if (pagerState.pageCount > 1) {
        HorizontalPagerIndicator(
        pagerState = pagerState,
        activeColor = Color("#32B5FF".toColorInt()),
        inactiveColor = Color("#C4C4C4".toColorInt()),
        modifier = Modifier.align(Center)
        )
        }




        Icon(
        painter = painterResource(id = R.drawable.wishlist),
        contentDescription = "wishlist",
        modifier = Modifier
        .size(24.dp)
        .align(CenterEnd)

        )
        }
         **/

        /** Like Section ( Show How Many Users Liked The Post )  **/
        LikeSection(post.likedBy)


        /** Post Description Section **/
        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(Color.Black, fontWeight = FontWeight.Bold)) {
                append("${post.userName} ")
            }
            append(post.description)
        }
        Text(
            text = annotatedString,
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier.padding(10.dp)
        )


    }
}


/** Like Section Composable Function **/
@Composable
fun LikeSection(likedBy: List<User>) {
    if (likedBy.size > 8) {
        Text(
            text = "${likedBy.size} likes",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    } else if (likedBy.size == 1) {
        Text(
            text = "Liked by ${likedBy[0].userName}",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )

    } else {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {

            PostLikeViewByProfile(likedBy)

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = "liked by ${likedBy[0].userName} and ${likedBy.size - 1} others",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }


    }
}


/** LikeSection : PostLikeViewByProfile (Showing likes) **/
@Composable
fun PostLikeViewByProfile(likedBy: List<User>) {

    LazyRow(horizontalArrangement = Arrangement.spacedBy((-5).dp)) {
        items(likedBy.take(3)) {

                likedBy ->
            Image(
                painter = painterResource(id = likedBy.profile),
                contentDescription = "story profile",
                modifier = Modifier
                    .size(22.dp)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .clip(CircleShape),

                contentScale = ContentScale.Crop
            )
        }
    }
}


/** Main Body : Posting / Feeds (Using View Pager ) **/
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostCarousel(postImageList: List<Int>, pagerState: PagerState) {

    Box(modifier = Modifier.fillMaxWidth()) {

        HorizontalPager(
            count = postImageList.size,
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { currentPage ->
            Image(
                painter = painterResource(id = postImageList[currentPage]),
                contentDescription = "post image ",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(375.dp),
                contentScale = ContentScale.Crop
            )
        }

        if (pagerState.pageCount > 1) {
            PostNudgeCount(
                modifier = Modifier
                    .align(TopEnd)
                    .padding(vertical = 12.dp, horizontal = 15.dp),
                pagerState
            )
        }


    }

}


/** Composable StorySection  **/
@Composable
fun StoriesSection(modifier: Modifier = Modifier, storyList: List<Stories>) {

    LazyRow() {
        items(storyList) { story ->
            StoryItem(modifier = Modifier, story = story)


        }

    }

}


/** Composable StoryItem UI Section Which Implements StoriesSection Function  **/
@Composable
fun StoryItem(modifier: Modifier, story: Stories) {

    Column(modifier = modifier.padding(5.dp)) {

        Image(
            painter = painterResource(id = story.profile),
            contentDescription = "story profile",
            modifier = Modifier
                .size(65.dp)
                .border(
                    width = 2.dp, brush = Brush.linearGradient(
                        listOf(
                            Color("#FFD700".toColorInt()),
                            Color("#FFD700".toColorInt())
                        )
                    ),
                    shape = CircleShape
                )
                .padding(5.dp)
                .clip(CircleShape),

            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))



        Text(
            text = story.userName,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(60.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )


    }
}


/** App Tool Bar , LOGO ,NOTIFICATION BUTTON MESSAGE BUTTON , POST BUTTON**/
@Composable
fun AppToolBar() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(modifier = Modifier.align(CenterEnd)) {
            Spacer(modifier = Modifier.width(18.dp))

            Icon(
                imageVector = Icons.Default.NotificationsNone,
                contentDescription = "notification",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(18.dp))

            Icon(
                painter = painterResource(id = R.drawable.more),
                contentDescription = "more options",
                modifier = Modifier.size(32.dp)


            )
        }
    }


}


/** Post Counter Box**/
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostNudgeCount(modifier: Modifier = Modifier, pagerState: PagerState) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Black.copy(0.4f))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(text = "${pagerState.currentPage + 1}", color = Color.White)
        Text(text = "/", color = Color.White)
        Text(text = "${pagerState.pageCount}", color = Color.White)


    }
}


/** Get The Users story Data without api**/
//fun getStories(): List<Stories> =
//    listOf(
//        Stories(userName = "Stories", profile = R.drawable.user),
//        Stories(userName = "Users", profile = R.drawable.user2),
//        Stories(userName = "Explore", profile = R.drawable.user3),
//        Stories(userName = "Mime TV", profile = R.drawable.user4),
//        Stories(userName = "Movies", profile = R.drawable.user5),
//        Stories(userName = "Mime TV", profile = R.drawable.user4),
//        Stories(userName = "Users", profile = R.drawable.user2),
//        Stories(userName = "Explore", profile = R.drawable.user3),
//
//        )
fun getStories(): List<Stories> = listOf(
        Stories(userName = "Stories", profile = R.drawable.user),
        Stories(userName = "Users", profile = R.drawable.user2),
        Stories(userName = "Explore", profile = R.drawable.user3),
        Stories(userName = "Mime TV", profile = R.drawable.user4),
        Stories(userName = "Movies", profile = R.drawable.user5),
        Stories(userName = "Mime TV", profile = R.drawable.user4),
        Stories(userName = "Users", profile = R.drawable.user2),
        Stories(userName = "Explore", profile = R.drawable.user3),

        )


/** Getting Post Data Without APi **/
fun getPostData(): List<Post> {
    val originalPost = Post(
        userName = "Aleena",
        profile = R.drawable.user_post,
        postImageList = listOf(R.drawable.user2, R.drawable.user4),
        description = "Lets down the haters",
        likedBy = listOf(
            User(profile = R.drawable.user, userName = "aleena"),
            User(profile = R.drawable.user2, userName = "jhonabraham"),
            User(profile = R.drawable.user3, userName = "arehim_himu"),
            User(profile = R.drawable.user4, userName = "Mr.x"),
            User(profile = R.drawable.user5, userName = "_starry_eyes")
        )
    )

    // Repeat the original post 10 times
    val repeatedPosts = List(10) { originalPost }

    return repeatedPosts
}


@Preview(showBackground = true)
@Composable
fun FeedPreview() {
    FeedScreen()
}

