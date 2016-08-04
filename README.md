# Week 3 - Tweets

Tweets is a Twitter OAuth application for Android.

Submitted by: Peyt S. Dewar II

Time spent:

Completed user stories:

 * [x] Required: Users can sign in to Twitter using OAuth login.
 * [x] Required: Each tweet should display the username, name, and its body.
 * [x] Required: Each tweet should display its relative timestamp.
 * [] Required: Users can view more tweets as they scroll with infinite pagination.
 * [] Required: Users can click a “Compose” icon in the Action Bar on the top right.
 * [] Required: Users can enter a new tweet and post it to twitter.
 * [] Required: Users are taken back to home timeline with new tweet visible in timeline. 
 * [] Optional: While composing a tweet, users can see a character counter with characters remaining for tweets out of 140.
 * [] Optional: Links in tweets are clickable and can launch the web browser.
 * [] Optional: Users can refresh timeline by pulling down to refresh.
 * [] Optional: Tweets are persisted into SQLite and can be displayed from the local DB, enabling the user to open the app offline to see the last loaded tweets.
 * [] Optional: Users can tap a tweet to display a detailed view of the tweet.
 * [] Optional: Users can select "reply" from the detail view to respond to a tweet.
 * [] Optional: UI and theme is improved to feel "Twitter branded".
 * [] Stretch: Users can see embedded image media within the tweet detail view.
 * [] Stretch: Users can watch a tweet's embedded video.
 * [] Stretch: ComposeActivity is replaced with a modal overlay.
 * [] Stretch: Parceler is used to make Article objects parcelable instead of serializable.
 * [] Stretch: ButterKnife annotation library is applied to reduce view boilerplate.
 * [x] Stretch: RecyclerView is leveraged as a replacement for the ListView and ArrayAdapter for all lists of tweets.
 * [] Stretch: "Compose" action is moved to a FloatingActionButton instead of on the AppBar.
 * [] Stretch: All icon drawables and other static image assets are replaced with vector drawables where appropriate.
 * [] Stretch: Data binding support module is leveraged to bind data into one or more activity layout templates.
 * [] Stretch: Picasso is replaced with Glide for more efficient image rendering.
 
## Video Walkthrough 

![Video Walkthrough] (___.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes


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
