# Football World Cup Score Board Java App

#### This is a Java app, presenting the Live Football World Cup Score Board which shows matches and scores.

Firstly, before writing any line of code, I sat down with notepad opened and wrote the happy path of going
through this app. This is the happy path that I've written:
### Happy path:
1. The user displayed a live scoreboard and there are no live matches.
2. The user displayed a summary and there are no past matches.
3. The user starts a game with home team Mexico and away team Canada. The score is 0-0
4. The user displayed a live scoreboard and there is one match Mexico-Canada with the score: 0-0.
5. The user starts a game with home team Spain and away team Brazil. The score is 0-0.
6. The user updated the score for the match Mexico-Canada: 3-1
7. The user displayed a live scoreboard and there are two matches: Mexico-Canada (3-1) and Spain-Brazil (0-0).
8. The user updated the score for the match Spain-Brazil: 1-1
9. The user finished the match Mexico-Canada.
10. The user displayed a live scoreboard and there is one match: Spain-Brazil (1-1).
11. The user starts a game with home team Argentina and away team Australia. The score is 0-0.
12. The user updated the score for the match Argentina-Australia: 2-2
13. The user finished the match Argentina-Australia.
14. The user starts a game with home team Poland and away team Italy.
15. The user updated the score for the match Poland-Italy: 5-4
16. The user finished the match Poland-Italy.
17. The user displayed the summary and there are three past matches in the following order:  Poland-Italy: (5-4), Argentina-Australia: (2-2) Mexico-Canada (3-1).

Then, after writing the happy path, I've decided to write the classes that I'd need to create. \
These are the classes used inside this app:
- `Game` (record) - it contains `UUID` for the game `id`, two teams (`Team` records - `homeTeam` and `awayTeam`) and two scores (`Score`records - `homeScore` and `awayScore`)
- `GameLiveScoreboard` - manages live/ongoing games
- `GameSummarizer` - manages finished games (summary) in an ordered list
- `GameLauncher` - validates and starts a new game
- `LiveFootballFacade` - simple facade used by unit tests

### These were my main things that I needed to analyze deeper:
1. Which type of list/map to use for keeping the live scoreboard?
    - I initially tried ArrayList but while updating or finishing a game, the list would need a full scan of its indexes with each action. So I started thinking of something else
    - I tried ConcurrentHashMap but its iteration order is undefined and thus changed between running unit tests, causing different order each time
    - After trying ConcurrentHashMap, I tried LinkedHashMap - it keeps the order, so that helped me with my unit tests.
2. Which type of list/map to use for the summary of past games?
   - I used an ArrayList - just because it is simple, there is no complicated logic - I never have to look through the summary by ID

