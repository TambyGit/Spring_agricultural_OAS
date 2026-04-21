package com.spring.tdfinaloas_collectivites_agricoles.service;

import org.springframework.stereotype.Service;
import com.spring.tdfinaloas_collectivites_agricoles.dao.MemberDao;
import com.spring.tdfinaloas_collectivites_agricoles.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public List<Member> createMembers(List<CreateMember> createMembers) throws SQLException {
        List<Member> createdMembers = new ArrayList<>();

        for (CreateMember createMember : createMembers) {

            if (createMember.getRegistrationFeePaid() == null || !createMember.getRegistrationFeePaid() ||
                    createMember.getMembershipDuesPaid() == null || !createMember.getMembershipDuesPaid()) {
                throw new IllegalArgumentException("Membership dues not paid or registration fee not paid");
            }


            if (createMember.getReferees() != null && !createMember.getReferees().isEmpty()) {
                for (RefereeInput refereeInput : createMember.getReferees()) {
                    if (!memberDao.existsById(refereeInput.getRefereeId())) {
                        throw new IllegalArgumentException("Member with bad referees: " + refereeInput.getRefereeId());
                    }
                }
            }


            String memberId = UUID.randomUUID().toString();
            Member member = new Member();
            member.setId(memberId);
            member.setFirstName(createMember.getFirstName());
            member.setLastName(createMember.getLastName());
            member.setBirthDate(createMember.getBirthDate());
            member.setGender(createMember.getGender());
            member.setAddress(createMember.getAddress());
            member.setProfession(createMember.getProfession());
            member.setPhoneNumber(createMember.getPhoneNumber());
            member.setEmail(createMember.getEmail());
            member.setOccupation(createMember.getOccupation());

            memberDao.save(member);


            Payment payment = new Payment();
            payment.setMemberId(memberId);
            payment.setCollectivityId(createMember.getCollectivityIdentifier());
            payment.setRegistrationFeePaid(createMember.getRegistrationFeePaid());
            payment.setMembershipDuesPaid(createMember.getMembershipDuesPaid());
            payment.setAmount(createMember.getAmount() != null ? createMember.getAmount() : 0.0);
            memberDao.savePayment(payment);
            member.setPayment(payment);


            Membership membership = new Membership();
            membership.setMemberId(memberId);
            membership.setCollectivityId(createMember.getCollectivityIdentifier());
            membership.setJoinDate(LocalDate.now());
            memberDao.saveMembership(membership);
            member.setMembership(membership);


            List<Referee> referees = new ArrayList<>();
            if (createMember.getReferees() != null) {
                for (RefereeInput refereeInput : createMember.getReferees()) {
                    Referee referee = new Referee();
                    referee.setMemberId(memberId);
                    referee.setRefereeId(refereeInput.getRefereeId());
                    referee.setRelationship(refereeInput.getRelationship());
                    memberDao.saveReferee(referee);

                    Optional<Member> refereeDetails = memberDao.findById(refereeInput.getRefereeId());
                    refereeDetails.ifPresent(referee::setRefereeDetails);
                    referees.add(referee);
                }
            }
            member.setReferees(referees);

            createdMembers.add(member);
        }

        return createdMembers;
    }
}
