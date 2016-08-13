# Week 3/4 - Tweets

Tweets is a Twitter OAuth application for Android.

Submitted by: Peyt S. Dewar II

Time spent: 20 hours total

Week 4 completed user stories:

The following **required** functionality is completed:
* [x] The app includes **all required user stories** from Week 3 Twitter Client
* [x] User can **switch between Timeline and Mention views using tabs**
  * [x] User can view their home timeline tweets.
  * [x] User can view the recent mentions of their username.
* [ ] User can navigate to **view their own profile**
  * [ ] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [ ] User can **click on the profile image** in any tweet to see **another user's** profile.
 * [ ] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
 * [ ] Profile view includes that user's timeline
* [ ] User can [infinitely paginate](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView) any of these timelines (home, mentions, user) by scrolling to the bottom.

The following **optional** features are implemented:
* [ ] User can view following / followers list through the profile
* [ ] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [ ] When a network request is sent, user sees an [indeterminate progress indicator](http://guides.codepath.com/android/Handling-ProgressBars#progress-within-actionbar)
* [ ] User can **"reply" to any tweet on their home timeline**
  * [ ] The user that wrote the original tweet is automatically "@" replied in compose
* [ ] User can click on a tweet to be **taken to a "detail view"** of that tweet
 * [ ] User can take favorite (and unfavorite) or retweet actions on a tweet
* [ ] Improve the user interface and theme the app to feel twitter branded
* [ ] User can **search for tweets matching a particular query** and see results
* [ ] Usernames and hashtags are styled and clickable within tweets [using clickable spans](http://guides.codepath.com/android/Working-with-the-TextView#creating-clickable-styled-spans)

The following **bonus** features are implemented:
* [ ] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [ ] Leverages the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into layout templates.
* [ ] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce view boilerplate.
* [ ] User can view their direct messages (or send new ones)

Week 3 completed user stories:

 * [x] Required: Users can sign in to Twitter using OAuth login.
 * [x] Required: Each tweet should display the username, name, and its body.
 * [x] Required: Each tweet should display its relative timestamp.
 * [x] Required: Users can view more tweets as they scroll with infinite pagination.
 * [x] Required: Users can click a “Compose” icon in the Action Bar on the top right.
 * [x] Required: Users can enter a new tweet and post it to twitter.
 * [x] Required: Users are taken back to home timeline with new tweet visible in timeline. 
 * [x] Optional: While composing a tweet, users can see a character counter with characters remaining for tweets out of 140.
 * [] Optional: Links in tweets are clickable and can launch the web browser.
 * [] Optional: Users can refresh timeline by pulling down to refresh.
 * [] Optional: Tweets are persisted into SQLite and can be displayed from the local DB, enabling the user to open the app offline to see the last loaded tweets.
 * [] Optional: Users can tap a tweet to display a detailed view of the tweet.
 * [] Optional: Users can select "reply" from the detail view to respond to a tweet.
 * [] Optional: UI and theme is improved to feel "Twitter branded".
 * [] Stretch: Users can see embedded image media within the tweet detail view.
 * [] Stretch: Users can watch a tweet's embedded video.
 * [] Stretch: ComposeActivity is replaced with a modal overlay.
 * [x] Stretch: Parceler is used to make Tweet objects parcelable instead of serializable.
 * [] Stretch: ButterKnife annotation library is applied to reduce view boilerplate.
 * [x] Stretch: RecyclerView is leveraged as a replacement for the ListView and ArrayAdapter for all lists of tweets.
 * [] Stretch: "Compose" action is moved to a FloatingActionButton instead of on the AppBar.
 * [] Stretch: All icon drawables and other static image assets are replaced with vector drawables where appropriate.
 * [] Stretch: Data binding support module is leveraged to bind data into one or more activity layout templates.
 * [] Stretch: Picasso is replaced with Glide for more efficient image rendering.
 
## Video Walkthrough 

![Video Walkthrough] (weekthreeDemo.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes
Spent some time working on the Tweet button and character count to look identical to Twitter's.

## License

    Copyright [2016] [Peyt S. Dewar II]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
