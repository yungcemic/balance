package me.yungcemic.balance.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import me.yungcemic.balance.player.BalancePlayer;
import org.bson.Document;

import java.util.Optional;
import java.util.UUID;

public final class MongoPlayerRepository implements PlayerRepository {

    private MongoClient client;
    private MongoCollection<Document> collection;

    public void connect(String uri, String databaseName, String collectionName) {
        this.client = MongoClients.create(uri);
        this.collection = this.client.getDatabase(databaseName).getCollection(collectionName);
    }

    @Override
    public Optional<BalancePlayer> findByUniqueId(UUID uniqueId) {
        Document document = collection.find(Filters.eq("uniqueId", uniqueId.toString())).first();
        if (document != null) {
            return Optional.of(new BalancePlayer(UUID.fromString(
                    document.getString("uniqueId")),
                    document.getDouble("balance")));
        }
        return Optional.empty();
    }

    @Override
    public void save(BalancePlayer player) {
        Document document = new Document().append("uniqueId", player.getUniqueId().toString());
        document.append("balance", player.getBalance());
        collection.replaceOne(Filters.eq("uniqueId", player.getUniqueId().toString()), document, new ReplaceOptions().upsert(true));
    }
}