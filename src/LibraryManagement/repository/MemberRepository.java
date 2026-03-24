package LibraryManagement.repository;

import LibraryManagement.model.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemberRepository {
    private final ConcurrentHashMap<String, Member> members = new ConcurrentHashMap<>();

    public void add(Member member) { members.put(member.getMemberId(), member); }
    public Optional<Member> findById(String id) { return Optional.ofNullable(members.get(id)); }
    public List<Member> findAll() { return new ArrayList<>(members.values()); }
    public int size() { return members.size(); }
}
