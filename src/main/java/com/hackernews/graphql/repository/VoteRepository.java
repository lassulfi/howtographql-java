package com.hackernews.graphql.repository;

import static com.mongodb.client.model.Filters.eq;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.hackernews.graphql.dataclasses.Vote;
import com.hackernews.graphql.scalars.Scalars;
import com.mongodb.client.MongoCollection;


public class VoteRepository {
	
	private final MongoCollection<Document> votes;
	
	public VoteRepository(MongoCollection<Document> votes) {
		this.votes = votes;
	}
	
	public List<Vote> findByUserId(String userId) {
		List<Vote> list = new ArrayList<>();
		
		
		for(Document doc : 	votes.find(eq("userId", userId))) {
			list.add(vote(doc));
		}
		
		return list;
	}
	
	public Vote saveVote(Vote vote) {
		Document doc = new Document();
		doc.append("userId", vote.getUserId());
		doc.append("linkId", vote.getLinkId());
		doc.append("createdAt", Scalars.dateTime.getCoercing().serialize(vote.getCreatedAt()));
		
		votes.insertOne(doc);
		
		return new Vote(doc.get("_id").toString(),
				vote.getCreatedAt(),
				vote.getUserId(),
				vote.getLinkId());
		
	}
	
	public Vote vote(Document doc) {
		return new Vote(doc.get("_id").toString(),
				ZonedDateTime.parse(doc.getString("createdAt")),
				doc.getString("userId"),
				doc.getString("linkId"));
	}

}
